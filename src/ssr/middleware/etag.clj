(ns ssr.middleware.etag
  (:require [clojure.string :as str]
            [pandect.algo.sha1 :as sha])
  (:import (java.io File)))

(defmulti calculate-etag class)

(defmethod calculate-etag String [s]
  (sha/sha1 s))

(defmethod calculate-etag File [f]
  (str (.lastModified f) "-" (.length f)))

(defmethod calculate-etag :default [x]
  nil)

(defn- not-modified [etag]
  {:status 304 :body "" :headers {"etag" etag}})

(defn wrap-etag [handler]
  (fn [req]
    (let [{body :body
           status :status
           {etag "etag"} :headers
           :as resp} (handler req)
          if-none-match (get-in req [:headers "if-none-match"])]
      (if (and etag (not= status 304))
        (if (= etag if-none-match)
          (not-modified etag)
          resp)
        (if-let [new-etag (calculate-etag body)]
          (if (= new-etag if-none-match)
            (not-modified new-etag)
            (assoc-in resp [:headers "etag"] new-etag))
          resp)))))
