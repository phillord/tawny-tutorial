;; ifndef::backend-slidy[]

;; [note]

;; [source,lisp]
;; ----
(ns lisbon.whats-in-a-name-s
  (:use [tawny owl])
  (:require [tawny.obo])
  (:require [clojure.string :as s]))
;; ----

;; endif:backend-slidy[]

;; [source,notlisp]
;; ----
;; (ns lisbon.whats-in-a-name
;;   (:use [tawny owl obo])
;;   (:require [clojure.string :as s)))
;; ----

;; [source,lisp]
;; ----
(defontology o)

;; => #<OWLClassImpl <8d9d3120-d374-4ffb-99d8-ffd93a7d5fdd#o#A>>
(defclass A
  :ontology o)
;; ----

;; [source,lisp]
;; ----
(defontology i
   :iri "http://www.w3id.org/ontolink/example/i")

(defclass B
  :ontology i)


;; ----

;; [source,lisp]
;; ----
(defontology r
  :iri "http://www.w3id.org/ontolink/example/r"
  :iri-gen (fn [ont name]
             (iri (str (as-iri ont)
                       "#"
                       (s/reverse name)))))

(defclass CDE
  :ontology r)
;; ----

;; [source,notlisp]
;; ----
;; (fn [ont name] <1>
;;   (iri         <2>
;;    (str <3>
;;     (as-iri ont) <4>
;;     "#" <5>
;;     (s/reverse name)))) <6>
;; ----



;; [source,lisp]
;; ----
(defontology obo
  :iri "http://www.w3id.org/ontolink/example/obo"
  :iri-gen tawny.obo/obo-iri-generate)

(tawny.obo/obo-restore-iri obo "./src/lisbon/whats_in_a_name.edn")

(defclass F
  :ontology obo)

(defclass G
  :ontology obo)

(defoproperty ro
  :ontology obo)

;; will not
(defclass H
  :ontology obo)
;; ----

;; [source,lisp]
;; ----
;; this stores any new IDs we have created
(tawny.obo/obo-store-iri obo "./src/lisbon/whats_in_a_name.edn")

;; this coins permanent IDS, in a controlled process!
(comment
  (tawny.obo/obo-generate-permanent-iri
   "./src/lisbon/whats_in_a_name.edn"
   "http://purl.obolibrary.org/obo/EXAM_"))
;; ----

;; [source,lisp]
;; ----
;; String building!
(defontology s)

(owl-class "J" :ontology s)
(object-property "r" :ontology s)
(owl-class "K"
           :ontology s
           :super (owl-some "r" "J"))

(comment
  (owl-class "L"
             :ontology s
             :super (owl-some r J)))


(owl-class "M"
           :ontology s
           :super "L")
;; ----


;; [source,omn]
;; ----
;; Class: s:M
;;     SubClassOf:
;;         s:L

;; Class: s:L
;; ----
