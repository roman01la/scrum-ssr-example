(ns ssr.components.db
  (:require [com.stuartsierra.component :as component]))

(defrecord Database [dbtype dbname host user password]
  component/Lifecycle
  (start [component]
    (if (:dbspec component)
      component
      (->> (into {} component)
          (assoc component :dbspec))))
  (stop [component]
    (dissoc component :dbspec)))

(defn new-database [host dbname user password]
  (map->Database {:dbtype "postgresql"
                  :dbname dbname
                  :host host
                  :user user
                  :password password}))
