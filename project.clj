(defproject ssr "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.521"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [com.stuartsierra/component "0.3.2"]
                 [ring "1.6.0-RC3"]
                 [aleph "0.4.3"]
                 [cheshire "5.7.1"]
                 [pandect "0.6.1"]
                 [amalloy/ring-gzip-middleware "0.1.3"]
                 [com.cognitect/transit-clj "0.8.300"]
                 [com.cognitect/transit-cljs "0.8.239"]
                 [hiccup "1.0.5"]
                 [org.roman01la/prum "0.10.8"]
                 [org.roman01la/scrum "2.3.0-SNAPSHOT"
                  :exclusions [rum]]
                 [bidi "2.0.17"]
                 [kibu/pushy "0.3.7"]]

  :plugins [[lein-cljsbuild "1.1.5" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src"]
                :compiler     {:main                 client.core
                               :preloads             [devtools.preload]
                               :asset-path           "/js/compiled/out"
                               :output-to            "resources/public/js/compiled/bundle.js"
                               :output-dir           "resources/public/js/compiled/out"
                               :source-map-timestamp true
                               :install-deps         true
                               :verbose              true}}]}

  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]
                                  [binaryage/devtools "0.9.4"]]
                   :source-paths ["dev"]}})
