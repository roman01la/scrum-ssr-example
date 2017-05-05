(ns ssr.resolver
  (:require [clojure.core.match :refer [match]]
            [ssr.api :as api]))

(defmulti resolver (fn [[key] _] key))

(defmethod resolver :router [[_ & path] req]
  (:ui/route req))

(defmethod resolver :top-posts [[_ & path] req]
  (api/top-posts (-> req :ui/route :route-params)))

(defmethod resolver :new-posts [[_ & path] req]
  (api/new-posts (-> req :ui/route :route-params)))

(defmethod resolver :show-posts [[_ & path] req]
  (api/show-posts (-> req :ui/route :route-params)))

(defmethod resolver :ask-posts [[_ & path] req]
  (api/ask-posts (-> req :ui/route :route-params)))

(defmethod resolver :job-posts [[_ & path] req]
  (api/job-posts (-> req :ui/route :route-params)))

(defmethod resolver :post [[_ & path] req]
  (api/post (-> req :ui/route :route-params)))

(defmethod resolver :user [[_ & path] req]
  (api/user (-> req :ui/route :route-params)))
