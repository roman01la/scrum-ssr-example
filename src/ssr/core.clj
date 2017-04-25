(ns ssr.core
  (:require [com.stuartsierra.component :as component]
            [ssr.components.app :refer [new-app]]
            [ssr.components.server :refer [new-server]]
            [ssr.components.db :refer [new-database]]
            [ssr.page :refer [render-page]]
            [ssr.sql :as sql]
            [ssr.entities.movie :as movie]
            [ui.core :refer [App]]))

(defn make-resolver [db]
  (fn [params]
    {[:movies] #(sql/exec db (movie/find-all))}))

(def system
  (component/system-map
    :database (new-database "localhost" "ssr" "roman01la" "admin")
    :app (component/using
           (new-app App make-resolver render-page)
           [:database])
    :server (component/using
              (new-server 3000)
              [:app])))
