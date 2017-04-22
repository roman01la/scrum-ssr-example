(ns ssr.server
  (:require [ring.middleware.params :refer [wrap-params]]
            [rum.core :as rum]
            [ssr.ui.core :refer [App]]))

(defn fetch-app [params]
  @(future (Thread/sleep 100)
           {:title "My App"
            :uname (get params "user")}))

(defn fetch-counter []
  @(future (Thread/sleep 100) 0))

(defn make-resolver [params]
  {[:app] #(fetch-app params)
   [:counter] fetch-counter})

;; Rum renderer middleware
(defn wrap-rum [handler ui-root make-resolver]
  (fn [req]
    (let [res (handler req)]
      (assoc res
        :body
        (->> (:params req)
             (make-resolver)
             (ui-root)
             (rum/render-html))))))

;; server handler
(defn handler [request]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    ""})

(def app
  {:handler
   (-> handler
       (wrap-rum App make-resolver)
       (wrap-params))})
