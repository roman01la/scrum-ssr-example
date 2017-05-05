(ns ssr.core
  (:require [com.stuartsierra.component :as component]
            [ssr.components.app :refer [new-app]]
            [ssr.components.server :refer [new-server]]
            [ssr.page :refer [render-page]]
            [ui.core :refer [App]]
            [ui.routes :refer [routes]]
            [ssr.api :as api]
            [ssr.resolver :refer [resolver]]))

;; simple one-time caching per request
(defn make-resolvers [resolver req]
  (let [cache (volatile! {})]
    (fn [[key & p :as path]]
      (if-let [data (get-in @cache path)]
        data
        (let [data (resolver [key] req)]
          (vswap! cache assoc key data)
          (get-in data p))))))

;; RPC API methods
(def rpc
  {"stories.top" api/top-posts
   "stories.new" api/new-posts
   "stories.show" api/show-posts
   "stories.ask" api/ask-posts
   "stories.job" api/job-posts
   "stories.by-id" api/post
   "users.by-id" api/user})

(def system
  (component/system-map
    :app (new-app App routes #(make-resolvers resolver %) render-page rpc)
    :server (component/using
              (new-server 3000)
              [:app])))
