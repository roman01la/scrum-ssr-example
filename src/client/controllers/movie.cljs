(ns client.controllers.movie)

(defmulti control (fn [action] action))

(defmethod control :init [_ [init-state]]
  init-state)
