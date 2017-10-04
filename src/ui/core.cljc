(ns ui.core
  (:require [rum.core :as rum]
            [scrum.core :as scrum]
            [sablono.util]
            [ui.header :refer [Header]]
            [ui.pages.about :as about-page]
            [ui.pages.top :as top-page]
            [ui.pages.new :as new-page]
            [ui.pages.show :as show-page]
            [ui.pages.ask :as ask-page]
            [ui.pages.job :as job-page]
            [ui.pages.user :as user-page]
            [ui.pages.post :as post-page]))

(def links
  [{:label "Top" :href "/" :route :top}
   {:label "New" :href "/new" :route :new}
   {:label "Show" :href "/show" :route :show}
   {:label "Ask" :href "/ask" :route :ask}
   {:label "Jobs" :href "/jobs" :route :jobs}])

(rum/defc App < rum/reactive [r]
  (let [{route :handler}
        (rum/react (scrum/subscription r [:router]))]
    [:div {}
     (Header links route)
     (case route
       :top (top-page/Layout r)
       :new (new-page/Layout r)
       :show (show-page/Layout r)
       :ask (ask-page/Layout r)
       :jobs (job-page/Layout r)
       :user (user-page/Layout r)
       :post (post-page/Layout r)
       :about (about-page/Layout r)
       nil)
     [:footer.page-footer]]))
