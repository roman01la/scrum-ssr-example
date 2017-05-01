(ns ssr.middleware.bidi
  (:require [bidi.bidi :as bidi]))

;; match request URI against client-side routes
(defn wrap-bidi [handler routes]
  (fn [req]
    (if-let [route (bidi/match-route routes (:uri req))]
      (-> req
          (assoc :ui/route route)
          handler)
      (handler req))))
