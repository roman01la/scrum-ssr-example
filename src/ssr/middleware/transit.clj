(ns ssr.middleware.transit
  (:require [cognitect.transit :as t]))

;; parse Transit encoded body into EDN
(defn wrap-transit-req [handler]
  (fn [req]
    (if (and (:body req)
             (= (get-in req [:headers "Content-Type"]) "application/transit+json"))
      (-> (:body req)
          (t/reader :json)
          t/read
          (->> (assoc req :body))
          handler)
      (handler req))))
