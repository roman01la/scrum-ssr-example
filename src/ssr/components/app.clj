(ns ssr.components.app
  (:require [com.stuartsierra.component :as component]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ssr.middleware.bidi :refer [wrap-bidi]]
            [ssr.middleware.rpc :refer [wrap-rpc]]
            [ssr.middleware.rum :refer [wrap-rum]]
            [ssr.middleware.transit :as transit]
            [ssr.middleware.etag :refer [wrap-etag]]))

(defn make-handler []
  (fn [req]
    {:status  200
     :headers {"Content-Type" "text/html"}
     :body    nil}))

(defrecord App [root-ui routes resolver render-page rpc]
  component/Lifecycle
  (start [component]
    (if (:app component)
      component
      (-> (make-handler)
          (wrap-rum root-ui resolver render-page)
          (wrap-bidi routes)
          (wrap-rpc "/api" rpc)
          (transit/wrap-transit-res)
          (transit/wrap-transit-req)
          (wrap-resource "public")
          (wrap-etag)
          (wrap-gzip)
          (->> (assoc component :app)))))
  (stop [component]
    (dissoc component :app)))

(defn new-app [root-ui routes resolver render-page rpc]
  (map->App {:root-ui root-ui
             :routes routes
             :resolver resolver
             :render-page render-page
             :rpc rpc}))
