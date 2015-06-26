(defproject lisbon "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :plugins [[lein-catnip "0.5.1" :exclusions [org.clojure/clojure]]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [uk.org.russet/tawny-owl "1.3.1-SNAPSHOT"]
                 [sio-maven "1.0.0"]]
  :main lisbon.core)
