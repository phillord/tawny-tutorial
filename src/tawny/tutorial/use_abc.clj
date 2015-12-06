;; == Task {counter:task}: Importing other Ontologies

;; * Understand how to _import_ and _use_ another Tawny-OWL ontology

;; == Importing

;; * Many ontologies import from other ontologies
;; * Allows cross-linking, and reuse of work
;; * Tawny-OWL supports this
;; * Involves two steps, *use* and *import*

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Many ontologies import from other ontologies e.g. GO and Relations
;; Ontology (RO)

;; Why import? Its good practice, allows cross-linking and reuse of work

;; Tawny supports this
;; ====
;; endif::backend-slidy[]

;; == The ontology to import

;; * The ontology we wish to import

;; [source,notlisp]
;; ----
;; include::abc.clj[]
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Aptly named abc.owl
;; ====
;; endif::backend-slidy[]

;; == Namespace

;; * We have seen `use` many times before
;; * A namespace with an ontology can be used like any other
;; * Here we use `require`
;; * Helps to avoid name collisions

;; [source,lisp]
;; ----
(ns tawny.tutorial.use-abc
  (:use [tawny.owl])
  (:require [tawny.tutorial.abc]))
;; ----

;; == Using

;; * Normally, using is not enough
;; * We also need to explicitly *import* the ontology
;; * Only after `owl-import` will its axioms become available
;; * *Warning:* Clojure has an `import` function and it does not do
;; the same thing

;; [source,lisp]
;; ----
(defontology myABC)

(owl-import tawny.tutorial.abc/abc)
;; ----

;; * Here, we define our new ontology and import the ontology
;; axioms from the other `abc` ontology

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; I did strongly consider combing the `use` or `require` step into
;; the `import`; this would have been (and still is for anyone who
;; wants to code it!) entirely possible. However, there are perfectly
;; valid reasons not to.

;; Without the import statement, we gain access to the *identifiers*
;; from the required ontology. And re-using the identifiers without
;; using all of the axioms from that ontology allows us to do some
;; useful things. For instance,
;; http://precedings.nature.com/documents/3576/version/1[MIREOT] does
;; exactly this. Or for a different take, consider my idea for
;; Ontology http://www.russet.org.uk/blog/2955[connection points].
;; ====
;; endif::backend-slidy[]

;; == Using
;; * The `require` statement also allows us to use symbols
;; * Here, we use an explicit name space `tawny.tutorial.abc`
;; * And a symbol `A`
;; * The symbolic approach protects against spelling mistakes

;; [source,lisp]
;; ----
(defclass MyA
  :super tawny.tutorial.abc/A)

(defclass MyB
  :super tawny.tutorial.abc/B)
;; ----

;; * The resulting OMN

;; [source,omn]
;; ----
;; Class: myABC:MyA
;;     SubClassOf:
;;         abc:A

;; Class: myABC:MyB
;;     SubClassOf:
;;         abc:B
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; If you get bored of typing, then you can also alias
;; `tawny.tutorial.abc` to a shorter form, as we saw earlier with
;; `clojure.string`.

;; Here, we are reusing basic Clojure functionality and its namespacing
;; mechanism. That the symbols refer to ontology terms really makes no
;; difference.
;; ====
;; endif::backend-slidy[]

;; == Task {task}: Conclusions

;; * `require` or `use` is a part of Clojure
;; * Gives us access to symbols from another namespace
;; * Ontologies still need to use `owl-import`
