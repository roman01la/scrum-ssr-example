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
          (let [state (atom {})
                reconciler (scrum/reconciler {:state state
                                              :resolvers (resolvers req)})]
            (-> reconciler
                (ui-root)
                (rum/render-html)
                (render-page @state))))))))
