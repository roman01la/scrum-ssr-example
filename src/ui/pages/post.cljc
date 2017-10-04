(ns ui.pages.post
  (:require [rum.core :as rum]
            [scrum.core :as scrum]
            [ui.base :as base]))

(rum/defc PostItem
  [{:keys [url title score by text]}]
  [:article.article-entry {}
   [:header {}
    [:h1 {} [:a {:href url} title]]
    [:div.meta {}
     [:span {} (str score " " (if (> score 1) "points" "point"))]
     [:span {} (str " by ")]
     [:a {:href (str "/user/" by)} by]]]
   (when text
     [:div.content {:dangerouslySetInnerHTML
                    {:__html text}}])])

(rum/defc Comment [render-comments {:keys [by time text kids]}]
  [:article.comment {}
   [:header.comment-header {}
    [:a {:href (str "/user/" by)} by]
    [:span {} " "]
    [:span.time {} (base/time-ago time)]]
   [:main.comment-body
    {:dangerouslySetInnerHTML {:__html text}}]
   (when (seq kids)
     (render-comments kids render-comments))])

(rum/defc CommentsList [comments render-comments]
  [:div.comments-list {}
   (->> comments
        (filter :text)
        (map #(rum/with-key (Comment render-comments %) (:time %))))])

(rum/defc Comments [num comments]
  [:div.comments {}
   [:div.header {}
    (if (> num 1)
      (str num " comments")
      (str num " comment"))]
   (CommentsList comments CommentsList)])

(rum/defc Layout < rum/reactive [r]
  (let [{:keys [descendants loading? kids] :as post}
        (rum/react (scrum/subscription r [:post]))]
    [:main.layout.post {}
     (when-not loading?
       [:div.page-content {}
        (PostItem (select-keys post [:url :title :score :by :text]))
        (Comments descendants kids)])]))
