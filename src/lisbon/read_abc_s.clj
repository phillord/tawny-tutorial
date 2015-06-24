(ns lisbon.read-abc-s
  (:use [tawny.owl])
  (:require [tawny.read]))

(tawny.read/defread abc
  :iri "http://www.w3id.org/ontolink/example/abcother.owl"
  :location (tawny.owl/iri (clojure.java.io/resource "abc.owl")))

(defontology myABC)

(owl-import abc)

(defclass MyA
  :super A)

(defclass MyB
  :super B)
