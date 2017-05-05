(ns ssr.middleware.transit
  (:require [cognitect.transit :as t])
  (:import (java.io ByteArrayOutputStream)))

;; parse Transit encoded request body into EDN
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

;; parse EDN encoded response body into Transit
(defn wrap-transit-res [handler]
  (fn [req]
    (let [res (handler req)]
      (if (= (get-in res [:headers "Content-Type"]) "application/transit+json")
        (let [out (ByteArrayOutputStream.)
              _ (t/write (t/writer out :json) (:body res))]
          (assoc res :body (.toString out)))
        res))))
