(ns ssr.middleware.rpc)

;; invoke RPC API methods
;; and encode response data into Transit format
(defn wrap-rpc [handler endpoint rpc]
  (fn [{:keys [uri request-method body] :as req}]
    (let [res (handler req)
          {:keys [jsonrpc id params method]} body]
      (if (and (= uri endpoint)
               (= request-method :post)
               jsonrpc)
        (if-let [method (get rpc method)]
          (let [data (method params)]
            (-> res
                (assoc-in [:headers "Content-Type"] "application/transit+json")
                (assoc :body {:jsonrpc "2.0"
                              :id id
                              :result data})))
          res)
        res))))
