(ns ssr.page
  (:import [java.io ByteArrayOutputStream])
  (:require [hiccup.page :as h]
            [cognitect.transit :as t]
            [clojure.data.json :as json]))

;; encode state hash into Transit format
(defn state->str [state]
  (let [out (ByteArrayOutputStream.)]
    (t/write (t/writer out :json) state)
    (json/write-str (.toString out))))

(defn render-page [content state]
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     [:link {:rel "stylesheet"
             :href "/css/main.css"}]]
    [:body
     [:div#app content]
     [:div#debug]
     [:script {:src "/js/compiled/bundle.js"}]
     [:script
      (str "client.core.init_app("
           (state->str state)
           ")")]]))
