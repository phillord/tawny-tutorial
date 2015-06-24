
;; [source,lisp]
(ns lisbon.autosave-s
  (:require [tawny.owl :as o])
  (:import [org.semanticweb.owlapi.model.OWLOntologyChangeListener]))
;; ----

;; [source,lisp]
(def auto-save-listener
  "The current listener for handling auto-saves or nil." (atom nil))
;; ----

;; [source,lisp]
;; ----
(defn auto-save
  "Autosave the current ontology everytime any change happens."
  ([o filename format]
   (let [listener (proxy [org.semanticweb.owlapi.model.OWLOntologyChangeListener]
               []
             (ontologiesChanged[l]
               (o/save-ontology o filename format)))])
   (reset! auto-save-listener listener)
   (.addOntologyChangeListener
    (o/owl-ontology-manager) listener)
   listener))
;; ----

;; [source,lisp]
;; ----
(defn auto-save-off
  "Stop autosaving ontologies."
  []
  (when @auto-save-listener
    (.removeOntologyChangeListener
     (o/owl-ontology-manager)
     @auto-save-listener)))
;; ----
