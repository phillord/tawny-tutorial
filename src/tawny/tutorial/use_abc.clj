;; == Task {counter:task}: Importing Other Ontologies

;; * Understand how to _import_ and _use_ another tawny ontology

;; == Importing

;; * Many Ontologies import other ontologies
;; * Allows cross-linking, and resuse of work
;; * Tawny supports this also

;; == An ontology to import

;; * However, reuse is inevitable
;; * And sometimes good
;; * So, how do we do it.
;; * In tawny, there are two steps, *use* and *import*

;; [source,notlisp]
;; ----
;; include::abc.clj[]
;; ----

;; == Using

;; * We have seen `use` many times before
;; * A namespace with an ontology can be `use`'d like any other
;; * Here we use `require`
;; * Helps to avoid name collisons

;; [source,lisp]
;; ----
(ns tawny.tutorial.use-abc
  (:use [tawny.owl])
  (:require [tawny.tutorial.abc]))
;; ----

;; == Using

;; * Normally, using is not enough
;; * We also need to explicitly *import* the ontology
;; * Only after import will its axioms become available
;; * Warning! Clojure has an `import` function and it does not do the same thing.

;; [source,lisp]
;; ----
(defontology useabc)

(owl-import tawny.tutorial.abc/abc)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; I did strongly consider combing the `use` or `require` step into the `import`;
;; this would have been (and still is for anyone who wants to code it!) entirely
;; possible. However, there are perfectly valid reasons not to.

;; Without the import statement, we gain access to the *identifiers* from the
;; `require`'d ontology. And re-using the identifiers without using all of the
;; axioms from that ontology allows us to do some useful things. For instance,
;; http://precedings.nature.com/documents/3576/version/1[MIREOT] does exactly
;; this. Or for a different take, consider my idea for Ontology
;; http://www.russet.org.uk/blog/2955[connection points].
;; ====
;; endif::backend-slidy[]


;; == Using

;; * Of course, we can also use IRIs direct from the imported ontology
;; * For which we need to use the `iri` function.
;; * This works with any ontology

;; [source,lisp]
;; ----
(defclass MyA
  :super (iri "http://www.w3id.org/ontolink/example/abc.owl#A"))
;; ----

;; == Using

;; * The `require` statement also allows us to use symbols
;; * Here, we use an explicit name space `tutorial.abc`
;; * And a symbol `B`.

;; [source,lisp]
;; ----
(defclass MyB
  :super tawny.tutorial.abc/B)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Here, we are reusing basic clojure functionality and it's namespacing
;; mechanism. That the symbols refer to ontology terms really makes no
;; difference.

;; If you get bored of typing, then you can also alias `tutorial.abc` to a shorter
;; form, as we saw earlier with `clojure.string`.
;; ====
;; endif::backend-slidy[]


;; == Using

;; * The final output is the same in both cases
;; * The symbolic approach (as always) protects against spelling mistakes!

;; [source,omn]
;; ----
;; Class: useabc:MyA
;;     SubClassOf:
;;         abc:A

;; Class: useabc:MyB
;;     SubClassOf:
;;         abc:B
;; ----

;; == Task {task}: Conclusions

;; * `require` or `use` is a part of Clojure
;; * Gives us access to symbols from another namespace
;; * Ontologies still need to use `owl-import`
