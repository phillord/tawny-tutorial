(defproject tawny.tutorial "1.0"
  :description "A Tutorial for Tawny-OWL"
  :url "http://github.com/phillord/tawny-tutorial"
  :plugins [[lein-catnip "0.5.1" :exclusions [org.clojure/clojure]]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [uk.org.russet/tawny-owl "1.4.0"]
                 [sio-maven "1.0.0"]]
  :main tawny.tutorial.core)
