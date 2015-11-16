;; == Task {counter:task}: Importing Other Ontologies

;; * Understand how to _import_ and _use_ another tawny ontology

;; == Importing

;; * Many Ontologies import other ontologies
;; * Allows cross-linking, and resuse of work
;; * Tawny supports this also


;; == J'accuse reuse

;; * Now I will rant!
;; * Is a belief that reuse is automatically good
;; * You should always use terms from another ontology
;; * I disagree! Consequences are serious!
;; * Duplicate, Duplicate, Duplicate
;; * After you've used five or six terms, then _consider_ refactoring
;; * Now I will stop ranting!


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; At this point I would like a rant. There has become a widely held believe,
;; typified (although not solely caused by) the believe that we should always
;; reuse terms from another ontology if they exist. And that this cross-linking
;; is necessarily good.

;; But this is not true. It is as at least as wrong in the ontology world as it
;; is in the software world. In software, reusing a library in your own is called
;; adding a *dependency*. You become *dependent* on it. You can be affected by
;; changes in it. Your release process can be affected by its release process. If
;; it rots, your project rots. If it is insecure, yours is insecure.

;; Not only this, you become dependent on its dependencies also. And, in fact,
;; the transitive closure of your dependencies. This project has over 70
;; dependencies. And these can change over time. While in "SNAPSHOT" mode, the
;; dependency graph of your project can change overnight, without any change to
;; your code. And you have multi-path problems, "OWL Hell", where one ontology
;; can be imported multiple times. And no one knows have to deal sanely with
;; versioning.

;; And this is just the software issue. With ontologies the problem is worse. You
;; are making an http://ontogenesis.knowledgeblog.org/1468[ontological
;; commitment]. And it may not be one that want, it may not be one that you agree
;; with, and it may be one that contradicts your actual use case.

;; Do not go contiually looking for terms that you can use and reuse. Consider
;; searching for terms when you realise that you are getting too far away from
;; the core requirements for your ontology, from your competency questions. If
;; you really like someone elses definition, cut-and-paste it, give it your own
;; identifiers, and comment that you have done so. Maybe, once you have five or
;; six terms from the same ontology, then *consider* importing the other
;; ontology. And don't blame me if it goes wrong.
;; ====
;; endif::backend-slidy[]

;; == An ontology to import

;; * However, reuse is inevitable
;; * And sometimes good
;; * So, how do we do it.
;; * In tawny, there are two steps, *use* and *import*

;; [source,notlisp]
;; ----
;; include::abc.clj[]
;; ----

;; == Using this ontology

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; [source,lisp]
;; ----
(ns tutorial.use-abc
  (:use [tawny.owl])
  (:require [tutorial.abc]))
;; ----
;; ====
;; endif::backend-slidy[]

;; == Using

;; * We have seen `use` many times before
;; * A namespace with an ontology can be `use`'d like any other
;; * Here we use `require`
;; * Helps to avoid name collisons

;; [source,notlisp]
;; ----
;; (ns tutorial.use-abc
;;   (:use [tawny.owl])
;;   (:require [tutorial.abc]))
;; ----

;; == Using

;; * Normally, using is not enough
;; * We also need to explicitly *import* the ontology
;; * Only after import will its axioms become available
;; * Warning! Clojure has an `import` function and it does not do the same thing.

;; [source,lisp]
;; ----
(defontology useabc)

(owl-import tutorial.abc/abc)
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
  :super tutorial.abc/B)
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

;; == Task {task}: Summary

;; * `require` or `use` is a part of Clojure
;; * Gives us access to symbols from another namespace
;; * Ontologies still need to use `owl-import`
