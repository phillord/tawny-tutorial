;; == Task {counter:task}: Reading an Ontology

;; * Understand what _reading_ an ontology achieves
;; * Use a simple example

;; == The problem

;; * We showed that we can import existing Tawny-OWL ontologies
;; * By using and importing the relevant namespace
;; * In order for this to work, we need the ontologies defined using Tawny-OWL
;; * What if they aren't?
;; * What if all we have are ontologies defined in other languages?
;; * Tawny-OWL supports this

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; In the previous task, we showed that we can import existing
;; Tawny-OWL ontologies by using and importing the namespace.

;; But in order for this to work, we need the Tawny source code; this
;; is not always possible

;; What happens if we do not have the original source code? Or worse,
;; what if it does not exist in the first place i.e. it was built
;; through a different means such as Protege?

;; ====
;; endif::backend-slidy[]

;; == Namespace

;; * As usual, we declare the namespace
;; * It is different
;; * `require` the `tawny.owl` and `tawny.read`
;; * Have access to the symbols but do not import them into the local
;; namespace
;; * Ensures that the namespace has nothing else in it
;; * Avoids namespace collisions

;; [source,lisp]
;; ----
(ns tawny.tutorial.read-abc
  (:require [tawny owl read]))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; As usual, let's start with declaring our namespace

;; Note that this namespace declaration is different; both `tawny.owl`
;; and `tawny.read` are required i.e. we have access to the symbols
;; but do not import them locally.

;; This ensures that nothing else is in it, if for no other reason
;; than to avoid namespace collisions.

;; ====
;; endif::backend-slidy[]

;; == Reading

;; * Tawny-OWL provides a solution called _reading_
;; * Reading makes all entities available as symbols

;; [source,lisp]
;; ----
(tawny.read/defread abc
  :iri "http://www.w3id.org/ontolink/example/abcother.owl"
  :location (tawny.owl/iri (clojure.java.io/resource "abcother.owl")))
;; ----

;; * In this case, a file `abcother.owl` has been saved locally
;; * Can read from any URL
;; * Highly configurable (e.g. filter and transform names)

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Tawny provides a solution to this problem called reading.

;; Reading makes all the entities available as symbols.

;; Also, note that we are using the OWL file from local, which gives
;; us a degree of flexibility -- you do not want to download GO
;; every time you restart the REPL.

;; Although not covered here, `defread` is highly configurable. You
;; can filter just the terms you want, change the names as you chose.
;; ====
;; endif::backend-slidy[]

;; == Reading

;; * Here, we define our new ontology and imports the ontology
;; axioms from other `abc` ontology

;; [source,lisp]
;; ----
(tawny.owl/defontology myABC)

(tawny.owl/owl-import abc)
;; ----

;; == Reading

;; * And access it's value by symbol
;; * Symbols must be defined

;; [source,lisp]
;; ----
(tawny.owl/defclass MyA
  :super A)

(tawny.owl/defclass MyB
  :super B)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Having read our ontology this now gives us the ability to refer
;; directly, with symbols. So, we can type `A` or `B`. This is safe,
;; and has all the advantages of symbol based definition.
;; ====
;; endif::backend-slidy[]

;; == Task {task}: Conclusions

;; * Tawny-OWL supports a read mechanism
;; * Ontologies only available as OWL files can be used transparently
