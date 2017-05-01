(ns ssr.core
  (:require [com.stuartsierra.component :as component]
            [ssr.components.app :refer [new-app]]
            [ssr.components.server :refer [new-server]]
            [ssr.page :refer [render-page]]
            [ui.core :refer [App]]
            [ui.routes :refer [routes]]
            [ssr.api :as api]))

;; server rendering state resolvers
(defn resolvers [req]
  {[:router] (constantly (:ui/route req))
   [:top-posts] #(api/top-posts (-> req :ui/route :route-params))
   [:new-posts] #(api/new-posts (-> req :ui/route :route-params))
   [:show-posts] #(api/show-posts (-> req :ui/route :route-params))
   [:ask-posts] #(api/ask-posts (-> req :ui/route :route-params))
   [:job-posts] #(api/job-posts (-> req :ui/route :route-params))
   [:user] #(api/user (-> req :ui/route :route-params))
   [:post] #(api/post (-> req :ui/route :route-params))})

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
    :app (new-app App routes resolvers render-page rpc)
    :server (component/using
              (new-server 3000)
              [:app])))
