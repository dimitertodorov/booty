(set-env!
  :source-paths #{"src/cljc" "src/cljs" "sass"}
  :resource-paths #{"resources" "compiled_css" "bower_components"}
  :dependencies '[[org.clojure/clojure "1.7.0"]
                  [org.clojure/clojurescript "1.8.40"]
                  [adzerk/boot-cljs "1.7.228-1" :scope "test"]
                  [adzerk/boot-cljs-repl "0.3.0" :scope "test"]
                  [adzerk/boot-reload "0.4.5" :scope "test"]
                  [pandeiro/boot-http "0.7.3" :scope "test"]
                  [adzerk/boot-test "1.1.1" :scope "test"]
                  [crisptrutski/boot-cljs-test "0.2.2-SNAPSHOT" :scope "test"]
                  [doo "0.1.7-SNAPSHOT" :scope "test"]
                  [com.cemerick/piggieback "0.2.1" :scope "test"]
                  [weasel "0.7.0" :scope "test"]
                  [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                  [prismatic/dommy "1.1.0"]
                  [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                  [sablono "0.6.3"]
                  [devcards "0.2.1-5" :exclusions [ cljsjs/react]]
                  [deraen/boot-sass "0.2.1"]
                  [degree9/boot-bower "0.3.0"]
                  [jayq "2.5.4"]
                  [secretary "1.2.3"]
                  [hiccups "0.3.0"]
                  [ring/ring-core "1.4.0"]
                  [cljs-ajax "0.5.4"]
                  [com.andrewmcveigh/cljs-time "0.4.0"]
                  [org.omcljs/om "1.0.0-alpha32" :exclusions [[org.clojure/clojure] [cljsjs/react]]]
                  [cljsjs/react-with-addons "0.14.7-0"]])

(require
  '[adzerk.boot-cljs :refer [cljs]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl repl-env]]
  '[adzerk.boot-reload :refer [reload]]
  '[adzerk.boot-test :refer :all]
  '[deraen.boot-sass :refer [sass]]
  '[degree9.boot-bower :refer [bower]]
  '[pandeiro.boot-http :refer [serve]]
  '[reps.server :refer [not-found-handler]]
  '[crisptrutski.boot-cljs-test :refer [test-cljs prep-cljs-tests run-cljs-tests exit!]])

(task-options!
  pom {:project     'torcaui
       :version     "0.1.0-SNAPSHOT"
       :description "TORCA CLJS UI"
       :license     {"The MIT License (MIT)" "http://opensource.org/licenses/mit-license.php"}}
  test-cljs {:js-env :phantom}
  cljs {:source-map true})

(deftask deps [] identity)

(deftask dev
         "Start the dev env..."
         [s speak bool "Notify when build is done"
          p port PORT int "Port for web server"]
         (comp
           (watch)
           (reload)
           (cljs-repl)
           (cljs :ids #{"main"})
           (serve :port port :dir "target" :not-found 'reps.server/not-found-handler  :reload true)
           (target)
           (if speak (boot.task.built-in/speak) identity)))

(deftask styles []
         "Compile Styles"
         (set-env! :source-paths #{"sass"}
                   :resource-paths #{})
        (comp (watch)
              (speak)
              (sass)
              (sift :include #{ #"([^\s]+(\.(?i)(css))$)"})
              (target :dir #{"compiled_css"})))

(deftask testing []
         (merge-env! :source-paths #{"test/cljs"})
         identity)


(deftask test-suite []
         (comp (testing)
               (test-cljs :exit? true
                          :suite-ns 'torcaui.suite)
               (exit!)))

(deftask test-all []
         (comp (testing)
               (test-cljs :exit? true
                          :cljs-opts {:foreign-libs [{:file "jquery/dist/jquery.min.js" :provides ["js.jquery"]}]}
                          :suite-ns 'torcaui.suite)
               (exit!)))

(deftask auto-test
         "Run Auto-Test"
         [s speak bool "Notify when build is done"]
         (comp (testing)
               (watch)
               (test-cljs
                 :cljs-opts {:foreign-libs [{:file "jquery/dist/jquery.min.js" :provides ["js.jquery"]}]}
                 :suite-ns 'torcaui.suite)
               (if speak (boot.task.built-in/speak) identity)))

(deftask package
         "Build the package"
         []
         (comp
           (cljs :compiler-options {
                                    :devcards false
                                    :optimizations :advanced
                                    :externs ["externs/jquery-1.9.js"]})

           (target)))
