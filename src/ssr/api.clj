(ns ssr.api
  (:require [cheshire.core :as c]))

(defn- posts [type {:keys [id]}]
  (let [id (Integer. (or id 1))
        size 20
        from (- (* id size) size)
        to (* id size)]
    (let [items (into [] (c/parse-string (slurp (str "data/" type "-posts.json")) true))]
      {:from from
       :to to
       :items
       (if (> to (count items))
         (subvec items from)
         (subvec items from to))
       :total (count items)})))

(defn top-posts [params]
  (-> (posts "top" params)
      (update :items (partial map #(dissoc % :kids)))))

(defn new-posts [params]
  (-> (posts "new" params)
      (update :items (partial map #(dissoc % :kids)))))

(defn show-posts [params]
  (-> (posts "show" params)
      (update :items (partial map #(dissoc % :kids)))))

(defn ask-posts [params]
  (-> (posts "ask" params)
      (update :items (partial map #(dissoc % :kids)))))

(defn job-posts [params]
  (-> (posts "job" params)
      (update :items (partial map #(dissoc % :kids)))))

(def comments
  (-> (slurp "data/comments.json")
      (c/parse-string true)))

(defn post [{:keys [id]}]
  (let [post (-> (slurp (str "https://hacker-news.firebaseio.com/v0/item/" id ".json"))
                 (c/parse-string true))]
    (->> id
         str
         keyword
         (get comments)
         :kids
         (assoc post :kids))))

(defn user [{:keys [id]}]
  (let [user
        (-> (c/parse-string (slurp "data/users.json") true)
            (get (keyword id)))]
    (update user :submitted count)))
