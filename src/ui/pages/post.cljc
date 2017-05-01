(ns ui.pages.post
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))

(rum/defc PostItem
  [{:keys [url title score by text]}]
  [:article.article-entry
   [:header
    [:h1 [:a {:href url} title]]
    [:div.meta
     [:span (str score " " (if (> score 1) "points" "point"))]
     [:span (str " by ")]
     [:a {:href (str "/user/" by)} by]]]
   (when text
     [:div.content {:dangerouslySetInnerHTML
                    {:__html text}}])])

(rum/defc Comments [num]
  [:div.comments
   [:div.header
    (if (> num 1)
      (str num " comments")
      (str num " comment"))]])

(rum/defc Layout < rum/reactive [r]
  (let [{:keys [descendants loading?] :as post}
        (rum/react (scrum/subscription r [:post]))]
    [:main.layout.post
     [:div.page-content
      (when-not loading?
        (PostItem (select-keys post [:url :title :score :by :text])))
      (Comments descendants)]]))
