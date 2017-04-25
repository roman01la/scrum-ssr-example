(ns ssr.components.app
  (:require [com.stuartsierra.component :as component]
            [ring.middleware.resource :refer [wrap-resource]]
            [ssr.middleware :refer [wrap-rum]]))

(defn make-handler []
  (fn [req]
    {:status  200
     :headers {"Content-Type" "text/html"}
     :body    nil}))

(defrecord App [root-ui make-resolver render-page database]
  component/Lifecycle
  (start [component]
    (if (:app component)
      component
      (-> (make-handler)
          (wrap-rum root-ui (make-resolver (:dbspec database)) render-page)
          (wrap-resource "public")
          (->> (assoc component :app)))))
  (stop [component]
    (dissoc component :app)))

(defn new-app [root-ui make-resolver render-page]
  (map->App {:root-ui root-ui
             :make-resolver make-resolver
             :render-page render-page}))
