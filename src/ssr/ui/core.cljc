(ns ssr.ui.core
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))

(rum/defc Header [title]
  [:h1 {} title])

(rum/defc Button
  [on-click label]
  [:button {:on-click on-click} label])

(rum/defc Counter < rum/reactive [r]
  [:div
   (Button #(scrum/dispatch! r :counter :dec) "-")
   [:span (rum/react (scrum/subscription r [:counter]))]
   (Button #(scrum/dispatch! r :counter :inc) "+")])

(rum/defc App < rum/reactive [r]
  (let [[title uname]
        (rum/react (scrum/subscription r [:app] (juxt :title :uname)))]
    [:div {}
     (Header title)
     [:p {} (str "Hello, " uname "!")]
     (Counter r)]))
