(defproject
  torcaui
  "0.1.0-SNAPSHOT"
  :dependencies
  [[adzerk/boot-cljs "1.7.228-1" :scope "test"]
   [adzerk/boot-cljs-repl "0.3.0" :scope "test"]
   [adzerk/boot-reload "0.4.5" :scope "test"]
   [pandeiro/boot-http "0.7.3" :scope "test"]
   [crisptrutski/boot-cljs-test "0.2.2-SNAPSHOT" :scope "test"]
   [org.clojure/clojure "1.7.0"]
   [org.clojure/clojurescript "1.7.228"]
   [adzerk/boot-test "1.1.1" :scope "test"]
   [com.cemerick/piggieback "0.2.1" :scope "test"]
   [weasel "0.7.0" :scope "test"]
   [org.clojure/tools.nrepl "0.2.12" :scope "test"]
   [org.clojure/core.async "0.1.346.0-17112a-alpha"]
   [sablono "0.6.3"]
   [devcards "0.2.1-5"]
   [deraen/boot-sass "0.2.1"]
   [degree9/boot-bower "0.3.0"]
   [jayq "2.5.4"]
   [secretary "1.2.3"]
   [hiccups "0.3.0"]
   [ring/ring-core "1.4.0"]
   [cljs-ajax "0.5.4"]
   [com.andrewmcveigh/cljs-time "0.4.0"]
   [org.omcljs/om "1.0.0-alpha32" :exclusions [org.clojure/clojure]]]
  :source-paths
  ["test/clj"
   "sass"
   "src/cljs"
   "src/cljc"
   "bower_components"
   "src/cljc"
   "compiled_css"
   "src/clj"
   "resources"])