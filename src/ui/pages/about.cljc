(ns ui.pages.about
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))

(rum/defc Layout [r]
  [:main.layout.about nil
   [:div.page-content nil
    [:article.article-entry nil
     [:h1 nil "Simple Hacker News Clone"]
     [:p nil "This is an example of a web app built using ClojureScript and Clojure backend."]]]])
