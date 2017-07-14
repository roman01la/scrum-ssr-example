(ns ui.pages.about
  (:require #?(:cljs [cljs.loader :as loader])
            [rum.core :as rum]
            [scrum.core :as scrum]))

(rum/defc Layout [r]
  [:main.layout.about
   [:div.page-content
    [:article.article-entry
     [:h1 "Simple Hacker News Clone"]
     [:p "This is an example of a web app built using ClojureScript and Clojure backend."]]]])
