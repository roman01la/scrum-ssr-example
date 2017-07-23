(ns ssr.core
  (:require [com.stuartsierra.component :as component]
            [ssr.components.app :refer [new-app]]
            [ssr.components.server :refer [new-server]]
            [ssr.page :refer [render-page]]
            [ui.core :refer [App]]
            [ui.routes :refer [routes]]
            [ssr.api :as api]
            [ssr.resolver :refer [make-resolver]]))

(defn resolve-subscription [make-resolver req]
  (make-resolver req))

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
    :app (new-app App routes #(resolve-subscription make-resolver %) render-page rpc)
    :server (component/using
              (new-server 3000)
              [:app])))
