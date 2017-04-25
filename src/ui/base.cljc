(ns ui.base
  (:require [rum.core :as rum]))

(rum/defc Field [props label]
  [:div {}
   [:label {} label]
   [:input props]])
