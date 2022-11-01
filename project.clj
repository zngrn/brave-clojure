(defproject brave-clojure "0.1.0-SNAPSHOT"
  :description "Collection of notes from Clojure for the Brave and True."
  :url "https://github.com/zngrn/brave-clojure"
  :license {:name "The MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot brave-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
