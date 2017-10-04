(ns ui.header
  (:require [rum.core :as rum]))

(rum/defc Logo < rum/static []
  [:svg {:viewBox "0 0 428 426"
         :width   30
         :height  30}
   [:g {}
    [:path {:fill "#96CA4B"
            :d    "M198.4,39.7l4.6-0.4V9.2l-5.4,0.4c-50.9,4.1-98.2,27-133.1,64.7C29.3,112.2,10,161.5,10,213\n  c0,51.5,19.3,100.8,54.5,138.6c34.9,37.7,82.2,60.6,133.1,64.7l5.4,0.4v-30.1l-4.6-0.4c-43.1-3.8-83-23.6-112.5-55.6\n  c-29.6-32.2-46-74-46-117.7c0-43.7,16.3-85.5,46-117.7C115.4,63.3,155.4,43.5,198.4,39.7z"}]
    [:path {:fill "#5F7FBF"
            :d    "M363.5,74.4c-34.9-37.7-82.2-60.6-133.1-64.7L225,9.2v30.1l4.6,0.4c43.1,3.8,83,23.6,112.5,55.6\n  c29.6,32.2,46,74,46,117.7c0,43.7-16.3,85.5-46,117.7c-29.5,32-69.4,51.8-112.5,55.6l-4.6,0.4v30.1l5.4-0.4\n  c50.9-4.1,98.2-27,133.1-64.7C398.7,313.8,418,264.5,418,213C418,161.5,398.7,112.2,363.5,74.4z"}]]])

(rum/defc NavItem [href label class item-class]
  [:li.nav-item {:class item-class}
   [:a.nav-link
    {:href  href
     :class class}
    label]])

(rum/defc Header [links curr-route]
  [:header.page-header {}
   [:nav.main-nav {}
    [:ol {}
     [:li.nav-item.logo {}
      [:a.nav-link {:href "/"} (Logo)]]
     (->> links
          (map (fn [{:keys [href label route]}]
                 (rum/with-key
                   (NavItem href label (when (= route curr-route) "active") nil)
                   route))))
     (NavItem "/about" "About" (when (= curr-route :about) "active") "right")]]])
