(ns ssr.sql
  (:require [clojure.java.jdbc :as j]
            [honeysql.core :as sql]))

(defn exec [dbspec query]
  (j/query dbspec (sql/format query)))
