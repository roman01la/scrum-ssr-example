(ns ssr.components.server
  (:require [com.stuartsierra.component :as component]
            [aleph.http :as http]))

(defrecord JettyServer [port app]
  component/Lifecycle
  (start [component]
    (if (:server component)
      component
      (let [handler (:app app)
            server (http/start-server (fn [req] (handler req)) {:port port})]
        (assoc component :server server))))
  (stop [component]
    (when-let [server (:server component)]
      (.close server))
    (dissoc component :server)))

(defn new-server [port]
  (map->JettyServer {:port port}))
