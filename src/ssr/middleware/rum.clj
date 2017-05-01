(ns ssr.middleware.rum
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))

;; render web app
(defn wrap-rum [handler ui-root resolvers render-page]
  (fn [req]
    (let [res (handler req)]
      (if-not (:ui/route req)
        res
        (assoc res
          :body
          (let [reconciler (-> (resolvers req) (scrum/reconciler))
                state (:state reconciler)]
            (-> reconciler
                (ui-root)
                (rum/render-html)
                (render-page @state))))))))
