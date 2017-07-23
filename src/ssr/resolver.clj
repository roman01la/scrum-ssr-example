(ns ssr.resolver
  (:require [clojure.core.match :refer [match]]
            [ssr.api :as api]))

(defn make-resolver [req]
  (let [params (-> req :ui/route :route-params)]
    {:router     #(:ui/route req)
     :top-posts  #(api/top-posts params)
     :new-posts  #(api/new-posts params)
     :show-posts #(api/show-posts params)
     :ask-posts  #(api/ask-posts params)
     :job-posts  #(api/job-posts params)
     :post       #(api/post params)
     :user       #(api/user params)}))
