(ns ui.core
  (:require [rum.core :as rum]
            [scrum.core :as scrum]
            [ui.base :as base]
            [ui.table :as table]))

(rum/defc App < rum/reactive [r]
  [:div
   [:header {}]
   (table/Table (rum/react (scrum/subscription r [:movies])))])
