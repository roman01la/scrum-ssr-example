(ns dev
  (:require
    [clojure.java.io :as io]
    [clojure.java.javadoc :refer [javadoc]]
    [clojure.pprint :refer [pprint]]
    [clojure.reflect :refer [reflect]]
    [clojure.repl :refer [apropos dir doc find-doc pst source]]
    [clojure.set :as set]
    [clojure.string :as string]
    [clojure.test :as test]
    [clojure.tools.namespace.repl :refer [refresh refresh-all clear]]
    [com.stuartsierra.component :as component]
    [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
    [ssr.components.server :refer [server]]
    [ssr.server :refer [app]]))



(clojure.tools.namespace.repl/set-refresh-dirs "dev" "src" "test")

(defn dev-system []
  (component/system-map
    :server (server app 3000)))

(set-init (fn [_] (dev-system)))
