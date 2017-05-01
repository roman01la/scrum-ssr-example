(ns ssr.middleware.rpc
  (:import (java.io ByteArrayOutputStream))
  (:require [cognitect.transit :as t]))

;; invoke RPC API methods
;; and encode response data into Transit format
(defn wrap-rpc [handler endpoint rpc]
  (fn [{:keys [uri request-method body] :as req}]
    (let [res (handler req)]
      (if (and (= uri endpoint)
               (= request-method :post)
               (contains? body :jsonrpc))
        (if-let [method (get rpc (:method body))]
          (let [out (ByteArrayOutputStream.)
                data (method (:params body))
                _ (t/write (t/writer out :json) data)]
            (-> res
                (assoc :body (.toString out))
                (assoc-in [:headers "Content-Type"] "application/transit+json")))
          res)
        res))))
