(ns scripts
  (:require [cheshire.core :as c]))

(defn- ->story-url [type]
  (str "https://hacker-news.firebaseio.com/v0/" type "stories.json"))

(defn- ->item-url [id]
  (str "https://hacker-news.firebaseio.com/v0/item/" id ".json"))

(defn- fetch [url]
  (->> (slurp url)
       c/parse-string))

;; get top posts
;(def top-ids
;  (fetch (->story-url "top")))

;(def top-posts
;  (map #(fetch (->item-url %)) top-ids))

;(->> top-posts
;     c/generate-string
;     (spit "top-posts.json"))

;; get new posts
;(def new-ids
;  (fetch (->story-url "new")))

;(def new-posts
;  (map #(fetch (->item-url %)) new-ids))

;(->> new-posts
;     c/generate-string
;     (spit "new-posts.json"))

;; get show posts
;(def show-ids
;  (fetch (->story-url "show")))

;(def show-posts
;  (map #(fetch (->item-url %)) show-ids))

;(->> show-posts
;     c/generate-string
;     (spit "show-posts.json"))

;; get ask posts
;(def ask-ids
;  (fetch (->story-url "ask")))

;(def ask-posts
;  (map #(fetch (->item-url %)) ask-ids))

;(->> ask-posts
;     c/generate-string
;     (spit "ask-posts.json"))

;; get job posts
;(def job-ids
;  (fetch (->story-url "job")))

;(def job-posts
;  (map #(fetch (->item-url %)) job-ids))

;(->> job-posts
;     c/generate-string
;     (spit "job-posts.json"))


;; get users
(defn- get-users [type]
  (->> (c/parse-string (slurp (str type "-posts.json")) true)
       (map :by)
       (into #{})))

(defn- fetch-user [user]
  (->> (str "https://hacker-news.firebaseio.com/v0/user/" user ".json")
       slurp
       c/parse-string))

;(def users
;  (->> ["top" "new" "show" "ask" "job"]
;       (map get-users)
;       (apply concat)
;       (into #{})
;       (pmap (fn [id] [id (fetch-user id)]))))

;(->> users
;     (into {})
;     c/generate-string
;     (spit "users.json"))

;; get comments
(defn- post [id]
  (-> (->item-url id)
      slurp
      (c/parse-string true)))

(defn- comments [id]
  (let [post (post id)]
    (if (seq (:kids post))
      (update post :kids (partial map comments))
      post)))

(defn- posts->ids [type]
  (->> (c/parse-string (slurp (str type "-posts.json")) true)
       (map :id)))

;(def all-ids
;  (->> ["top" "new" "show" "ask" "job"]
;       (map posts->ids)
;       (apply concat)))

;(->> (subvec (vec all-ids) 1000 1177)
;     (map (fn [id] [(str id) (comments id)]))
;     (into {})
;     c/generate-string
;     (spit "comments_1000-1177.json"))

;     (pmap (fn [id] [id (comments id)]))
;     (into {})
;     c/generate-string
;     (spit "comments.json"))

;(defn count-comments- [sum comment]
;  (let [comments (get comment "kids")]
;    (if (seq comments)
;      (+ sum (reduce count-comments- 1 comments))
;      (inc sum))))
;
;(defn count-comments [[id post]]
;  [id
;   (reduce count-comments- 0 (get post "kids"))])
;
;(def ccount
;  (->> (slurp "comments.json")
;       c/parse-string
;       (map count-comments)
;       (into {})))
;
;(->> (slurp "show-posts.json")
;     c/parse-string
;     (map #(assoc % "comments" (get ccount (str (get % "id")))))
;     c/generate-string
;     (spit "show-posts.json"))
