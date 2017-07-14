(ns ui.pages.user
  (:require #?(:cljs [cljs.loader :as loader])
            [rum.core :as rum]
            [scrum.core :as scrum]))

(defn- format-date [ts]
  (let [date #?(:clj (java.util.Date. (* ts 1000))
                :cljs (js/Date. (* ts 1000)))
        day (.getDate date)
        month (.getMonth date)
        year (-> date .getYear (+ 1900))]
    (str day "/" month "/" year)))

(rum/defc InfoItem [[field value]]
  [:li.about-item
   [:span.label
    (case field
      :id "user"
      :submitted "posts submitted"
      (name field))]
   [:span.value
    (case field
      :created (format-date value)
      :about {:dangerouslySetInnerHTML
              {:__html value}}
      value)]])

(rum/defc Layout < rum/reactive [r]
  (let [user (rum/react! (scrum/subscription r [:user]))]
    [:main.layout
     [:div.page-content
      [:ul.about-list
       (->> (select-keys user [:id :created :about :karma :submitted])
            (filter (comp some? second))
            (map #(rum/with-key (InfoItem %) (first %))))]]]))
