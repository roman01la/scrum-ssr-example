(ns client.router
  (:require [cljs.loader :as loader]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]))

(defn start! [on-set-page routes]
  (let [set-page! (fn [match] (loader/load (:handler match) #(on-set-page match)))
        history (pushy/pushy set-page! (partial bidi/match-route routes))]
    (pushy/start! history)
    history))

(defn stop! [history]
  (pushy/stop! history))
