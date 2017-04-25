(ns client.core
  (:require [rum.core :as rum]
            [scrum.core :as scrum]
            [cognitect.transit :as t]
            [ui.core :refer [App]]
            [client.controllers.movie :as movie]))

(defonce reconciler
  (scrum/reconciler
    {:state (atom {})
     :controllers
     {:movies movie/control}}))

(defn render []
  (rum/mount (App reconciler)
             (. js/document (getElementById "app"))))

(defn ^:export init-app [state]
  (let [state (t/read (t/reader :json) state)]
    (doseq [[ctrl init-state] state]
      (scrum/dispatch-sync! reconciler ctrl :init init-state))
    (render)))
