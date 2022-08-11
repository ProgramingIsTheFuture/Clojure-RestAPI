(defproject rest-api "0.1.0-SNAPSHOT"
  :description "Rest api"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :main rest-api.core
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.5.1"]
                 [ring "1.9.5"]]
  :plugins [[lein-ring "0.12.5"]]
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
