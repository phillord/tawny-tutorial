;; == Task {counter:task}: Reading an Ontology

;; * Understand what _reading_ an ontology achieves
;; * Use a simple example

;; == The problem

;; * In the previous example, `abc.owl` was developed in Tawny-OWL
;; * We need the Tawny-OWL source code for this to work
;; * What if we do not have it?
;; * Or, worse, what if it does not exist?

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; It is possible that you want access to an OWL ontology that was not developed
;; using Tawny-OWL, but by some legacy editor like Protege?
;; ====
;; endif::backend-slidy[]

;; == Namespace

;; * As usual, we define a namespace for our experiments!

;; [source,notlisp]
;; ----
;; (ns tutorial.read-abc
;;   (:use [tawny.owl])
;;   (:require [tawny.read]))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; [source,lisp]
;; ----
(ns tawny.tutorial.read-abc
  (:use [tawny.owl])
  (:require [tawny.read]))
;; ----
;; ====
;; endif::backend-slidy[]

;; == Solution 1

;; * We could `owl-import` a IRI
;; * And refer to all entities by IRI
;; * Painful
;; * Error prone
;; * And with OBO identifiers, untenable


;; == Solution 2

;; * Tawny-OWL provides a solution called _reading_
;; * Reading makes all entities available as symbols
;; * In this case, a file `abcother.owl` has been saved locally
;; * Can read from any URL.

;; [source,lisp]
;; ----
(tawny.read/defread abc
  :iri "http://www.w3id.org/ontolink/example/abcother.owl"
  :location (tawny.owl/iri (clojure.java.io/resource "abcother.owl")))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Normally, you read an ontology into a namespace with nothing else in it, if
;; for no other reason than to avoid name collisons.

;; Also, note that we are using the OWL file from local, which gives us a degree
;; of flexibility -- you do not want to download GO everytime you restart the REPL.

;; Although not covered here, `defread` is highly configurable. You can filter
;; just the terms you want, change the names as you chose.
;; ====
;; endif::backend-slidy[]

;; == Reading

;; * Now we define our new ontology and import ABC

;; [source,lisp]
;; ----
(defontology myABC)

(owl-import abc)
;; ----

;; == Reading

;; * And access it's value by symbol
;; * Symbols must be defined.

;; [source,lisp]
;; ----
(defclass MyA
  :super A)

(defclass MyB
  :super B)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Having read our ontology this now gives us the ability to refer directly, with
;; symbols. So, we can type `A` or `B`. This is safe, and has all the advantages
;; of symbol based definition.
;; ====
;; endif::backend-slidy[]


;; == Task {task}: Conclusion

;; * Tawny-OWL supports a read mechanism
;; * Ontologies only available as OWL files can be used transparently
