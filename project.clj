(defproject ssr "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.521"]
                 [com.stuartsierra/component "0.3.2"]
                 [ring "1.6.0-RC3"]
                 [aleph "0.4.3"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [honeysql "0.8.2"]
                 [com.cognitect/transit-clj "0.8.300"]
                 [com.cognitect/transit-cljs "0.8.239"]
                 [hiccup "1.0.5"]
                 [rum "0.10.8"]
                 [org.roman01la/scrum "2.0.0-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.1.5" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :compiler {:main client.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/bundle.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true}}]}

  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]]
                   :source-paths ["dev"]}})
