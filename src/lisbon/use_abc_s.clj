(ns lisbon.use-abc-s
  (:use [tawny.owl])
  (:require [lisbon.abc]))


(defontology useabc)

(owl-import lisbon.abc/abc)

(defclass MyA
  :super (iri "http://www.w3id.org/ontolink/example/abc.owl#A"))

(defclass MyB
  :super lisbon.abc/B)

;; Class: useabc:MyA
;;     SubClassOf:
;;         abc:A

;; Class: useabc:MyB
;;     SubClassOf:
;;         abc:B
