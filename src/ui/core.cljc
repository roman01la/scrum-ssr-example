(ns ui.core
  (:require [rum.core :as rum]
            [scrum.core :as scrum]
            [ui.header :refer [Header]]
            #?(:clj [ui.pages.about :as about-page])
            #?(:clj [ui.pages.top :as top-page])
            #?(:clj [ui.pages.fresh :as new-page])
            #?(:clj [ui.pages.show :as show-page])
            #?(:clj [ui.pages.ask :as ask-page])
            #?(:clj [ui.pages.job :as job-page])
            #?(:clj [ui.pages.user :as user-page])
            #?(:clj [ui.pages.post :as post-page])))

(def links
  [{:label "Top" :href "/" :route :top}
   {:label "New" :href "/new" :route :new}
   {:label "Show" :href "/show" :route :show}
   {:label "Ask" :href "/ask" :route :ask}
   {:label "Jobs" :href "/jobs" :route :jobs}])

(rum/defc App < rum/reactive [r]
  (let [{route :handler}
        (rum/react! (scrum/subscription r [:router]))]
    [:div
     (Header links route)
     #?(:clj
         (case route
           :top (top-page/Layout r)
           :new (new-page/Layout r)
           :show (show-page/Layout r)
           :ask (ask-page/Layout r)
           :jobs (job-page/Layout r)
           :user (user-page/Layout r)
           :post (post-page/Layout r)
           :about (about-page/Layout r)
           nil))
     #?(:cljs
         (case route
           :top ((resolve 'ui.pages.top/Layout) r)
           :new ((resolve 'ui.pages.fresh/Layout) r)
           :show ((resolve 'ui.pages.show/Layout) r)
           :ask ((resolve 'ui.pages.ask/Layout) r)
           :jobs ((resolve 'ui.pages.job/Layout) r)
           :user ((resolve 'ui.pages.user/Layout) r)
           :post ((resolve 'ui.pages.post/Layout) r)
           :about ((resolve 'ui.pages.about/Layout) r)
           nil))
     [:footer.page-footer]]))
