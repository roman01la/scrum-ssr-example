(ns ssr.entities.movie)

(defn find-all []
  {:select [:*]
   :from [:movie]})
