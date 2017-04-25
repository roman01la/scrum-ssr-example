(ns ssr.middleware
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))

(defn wrap-rum [handler ui-root make-resolver render-page]
  (fn [req]
    (let [res (handler req)]
      (assoc res
        :body
        (let [reconciler (-> (:params req) (make-resolver) (scrum/reconciler))
              state (:state reconciler)]
          (-> reconciler
              (ui-root)
              (rum/render-html)
              (render-page @state)))))))
