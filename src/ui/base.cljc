(ns ui.base
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))

(defn- get-date []
  #?(:clj  (java.util.Date.)
     :cljs (js/Date.)))

(defn time-ago [ts]
  (let [s (-> (get-date) .getTime (- (* ts 1000)) (/ 1000))
        m (int (/ s 60))
        h (int (/ m 60))
        d (int (/ h 24))]
    (cond
      (>= d 2) (str d " days ago")
      (>= d 1) (str d " day ago")
      (>= h 2) (str h " hours ago")
      (>= h 1) (str h " hour ago")
      (>= m 2) (str m " minutes ago")
      (>= m 1) (str m " minute ago")
      (>= s 2) (str s " seconds ago")
      (>= s 1) (str s " second ago")
      :else "now")))

(rum/defc Pagination
  [{:keys [slug total current]}]
  [:div.pagination {}
   (cond
     (= current 2)
     [:a {:href (str "/" (when-not (= slug "top") slug))} "< prev"]

     (> current 2)
     [:a {:href (str "/" slug "/" (dec current))} "< prev"]

     :else [:a.disabled {} "< prev"])
   [:span {} (str current "/" total)]
   (if (< current total)
     [:a {:href (str "/" slug "/" (inc current))} "next >"]
     [:a.disabled {} "next >"])])

(rum/defc PostItem
  [{:keys [title url score by id comments time]}]
  [:article.article-entry {}
   [:header {}
    [:h2 {} [:a {:href url} title]]]
   [:footer {}
    [:span {} (str score " points by ")]
    [:a {:href (str "/user/" by)} by]
    [:span {} (str " " (time-ago time) " | ")]
    [:a {:href (str "/item/" id)}
     (cond
       (nil? comments) "discuss"
       (zero? comments) "discuss"
       :else (str comments " comments"))]]])

