(ns client.controllers.router)

(defmulti control (fn [action] action))

(defmethod control :init [_ [route] _]
  {:state route})

(defmethod control :push [_ [route] _]
  {:state route})
