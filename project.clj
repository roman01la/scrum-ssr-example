(defproject ssr "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.787"]
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
                 [org.roman01la/scrum "2.2.0-SNAPSHOT"
                  :exclusions [rum]]
                 [bidi "2.0.17"]
                 [kibu/pushy "0.3.7"]

                 ;; sablono deps
                 [org.omcljs/om "1.0.0-alpha48"]]

  :plugins [[lein-cljsbuild "1.1.5" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src" "../rum/src" "../sablono/src"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src" "../rum/src" "../sablono/src"]
                :compiler {:asset-path "/js/compiled/out-dev"
                           :output-dir "resources/public/js/compiled/out-dev"
                           :npm-deps {"react" "15.4.2"
                                      "react-dom" "15.4.2"}
                           :modules {:cljs-base {:output-to "resources/public/js/compiled/out-dev/common.js"}
                                     :app {:entries #{client.core}
                                           :output-to "resources/public/js/compiled/out-dev/app.js"}
                                     :about {:entries #{ui.pages.about}
                                             :output-to "resources/public/js/compiled/out-dev/about.js"}
                                     :top {:entries #{ui.pages.top}
                                           :output-to "resources/public/js/compiled/out-dev/top.js"}
                                     :new {:entries #{ui.pages.fresh}
                                           :output-to "resources/public/js/compiled/out-dev/fresh.js"}
                                     :show {:entries #{ui.pages.show}
                                            :output-to "resources/public/js/compiled/out-dev/show.js"}
                                     :ask {:entries #{ui.pages.ask}
                                           :output-to "resources/public/js/compiled/out-dev/ask.js"}
                                     :jobs {:entries #{ui.pages.job}
                                            :output-to "resources/public/js/compiled/out-dev/jobs.js"}
                                     :user {:entries #{ui.pages.user}
                                            :output-to "resources/public/js/compiled/out-dev/user.js"}
                                     :post {:entries #{ui.pages.post}
                                            :output-to "resources/public/js/compiled/out-dev/post.js"}}
                           :preloads [process.env devtools.preload]
                           :closure-warnings {:non-standard-jsdoc :off
                                              :global-this :off}
                           :source-map-timestamp true
                           :verbose true}}
               {:id "min"
                :source-paths ["src" "../rum/src" "../sablono/src"]
                :compiler {:asset-path "/js/compiled/out-min"
                           :output-dir "resources/public/js/compiled/out-min"
                           :optimizations :advanced
                           :npm-deps {"react" "15.4.2"
                                      "react-dom" "15.4.2"}
                           :modules {:cljs-base {:output-to "resources/public/js/compiled/out-min/common.js"}
                                     :app {:entries #{client.core}
                                           :output-to "resources/public/js/compiled/out-min/app.js"}
                                     :about {:entries #{ui.pages.about}
                                             :output-to "resources/public/js/compiled/out-min/about.js"}
                                     :top {:entries #{ui.pages.top}
                                           :output-to "resources/public/js/compiled/out-min/top.js"}
                                     :new {:entries #{ui.pages.fresh}
                                           :output-to "resources/public/js/compiled/out-min/fresh.js"}
                                     :show {:entries #{ui.pages.show}
                                            :output-to "resources/public/js/compiled/out-min/show.js"}
                                     :ask {:entries #{ui.pages.ask}
                                           :output-to "resources/public/js/compiled/out-min/ask.js"}
                                     :jobs {:entries #{ui.pages.job}
                                            :output-to "resources/public/js/compiled/out-min/jobs.js"}
                                     :user {:entries #{ui.pages.user}
                                            :output-to "resources/public/js/compiled/out-min/user.js"}
                                     :post {:entries #{ui.pages.post}
                                            :output-to "resources/public/js/compiled/out-min/post.js"}}
                           :externs ["externs/react.js"]
                           :closure-defines {"process.env.NODE_ENV" "production"}
                           :closure-warnings {:non-standard-jsdoc :off
                                              :global-this :off}
                           :source-map-timestamp true
                           :verbose true}}]}

  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]
                                  [binaryage/devtools "0.9.4"]]
                   :source-paths ["dev"]}})
