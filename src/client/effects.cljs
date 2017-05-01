(ns client.effects
  (:require [scrum.core :as scrum]
            [cognitect.transit :as t]))

(defn- ->rpc [method params]
  {:jsonrpc "2.0"
   :method method
   :params params
   :id (gensym "rpc")})

(defn- ->transit [data]
  (t/write (t/writer :json) data))

(defn- ->json [data]
  (t/read (t/reader :json) data))

(defn rpc [r c {:keys [url method params on-success on-error]}]
  (-> (js/fetch url #js {:method "POST"
                         :body (->transit (->rpc method params))
                         :headers
                         (doto (js/Headers.)
                           (.append "Content-Type" "application/transit+json"))})
      (.then #(.text %))
      (.then ->json)
      (.then #(scrum/dispatch! r c on-success %))
      (.catch #(scrum/dispatch! r c on-error %))))
