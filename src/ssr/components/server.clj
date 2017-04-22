(ns ssr.components.server
  (:import org.eclipse.jetty.server.Server)
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]))

(defrecord JettyServer [app]
  component/Lifecycle
  (start [component]
    (if (:server component)
      component
      (let [options (-> component (dissoc :app) (assoc :join? false))
            handler (->> app :handler delay atom)
            server (jetty/run-jetty (fn [req] (@@handler req)) options)]
        (assoc component
          :server server
          :handler handler))))
  (stop [component]
    (if-let [^Server server (:server component)]
      (do (.stop server)
          (.join server)
          (dissoc component :server :handler))
      component)))

(defn server [app port]
  (map->JettyServer {:app app :port port}))
