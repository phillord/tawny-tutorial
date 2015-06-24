(ns lisbon.abc
  (:use [tawny.owl]))


(defontology abc
  :iri "http://www.w3id.org/ontolink/example/abcother.owl"
  :noname true)

(defclass A)
(defclass B)
(defclass C)

;; save this for later examples!
(save-ontology "resources/abc.owl" :owl)
