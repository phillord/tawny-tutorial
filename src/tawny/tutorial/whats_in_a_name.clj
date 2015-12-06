;; == Task {counter:task}: Understanding Names

;; * Understand how Tawny-OWL uses IRIs and symbols to identify entities
;; * See how to use them independently
;; * Understand how they allow OBO ID support

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Let's leave our amino acid ontology for now to discuss how
;; Tawny-OWL uses IRIs and Clojure symbols to refer to ontology
;; entities.

;; These IRIs and symbols can be independent which allows Tawny to
;; support OBO IDs

;; ====
;; endif::backend-slidy[]

;; == Namespace

;; * `tawny.obo` to support (OBO) numeric IDs
;; * `clojure.string` is for string manipulation

;; [source,lisp]
;; ----
(ns tawny.tutorial.whats-in-a-name
  (:use [tawny.owl])
  (:require [tawny.obo])
  (:require [clojure.string :as s]))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Usual Clojure namespace declaration for this task
;; ====
;; endif::backend-slidy[]

;; == Background

;; * Ontology entities need names
;; * Tawny-OWL has different requirements for names
;; * We need to support alternative workflows
;; * So, Tawny-OWL is flexible
;; * With "sensible" defaults

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; First a bit a background:

;; Ontologies entities need names to be able to refer to them

;; As a community we typically use IRIs but Tawny-OWL has different
;; requirements for names (symbols); something that is valid as an IRI
;; is not necessarily valid as a (Clojure) symbol

;; In addition, we need to support alternative workflows e.g. OBO
;; numeric IDs.

;; So Tawny is flexible; IRIs and tawny names can be independent and
;; has been built with "sensible" defaults

;; ====
;; endif::backend-slidy[]


;; == IRIs

;; * Tawny-OWL is built on the OWL API
;; * Underneath, therefore, it is part of the web
;; * OWL uses IRIs (i.e. URIs or URLs) to identify entities
;; * IRIs provide a single, shared global namespace
;; * With a (social) mechanism for uniqueness

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Tawny is built on the OWL API, which means that underneath it is a
;; part of the web.

;; We have to inherit directly from the web, because all the software
;; that we are building on depends on it and/or requires it.

;; OWL uses IRIs and they have this unusual characteristic of being
;; global.
;; ====
;; endif::backend-slidy[]

;; == Symbols

;; * Tawny-OWL uses symbols to identify entities
;; * Core feature of Clojure
;; * Easy to type (e.g. `A` rather than `"A"`)
;; * Allows you handle them directly
;; * Provides define-before-use semantics
;; * Supported in IDEs (e.g. syntax highlight and auto-complete)

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; In contrast, Tawny uses symbols to identify entities.

;; Symbols are a core feature of Clojure and any Lisp; They are kind
;; of equivalent to variable names in other programming languages, but
;; not exactly the same.

;; Using symbols provides a set of advantages:
;; - They are easy to type e.g. A rather "A"
;; - Lisp gives you flexibility to handle them directly
;; - encourage define-before-use semantics
;; - are normally syntax highlighted specially by editors and, rather
;; usefully, they will normally auto-complete ("code-complete"
;; or "intellisense") in an IDE. Very useful for big ontologies

;; ====
;; endif::backend-slidy[]

;; == Symbols and IRIs

;; * What is the relationship between symbols and IRIs?
;; * In Tawny-OWL, this is a per-ontology setting
;; * By default the symbol forms the fragment of the IRI

;; [source,lisp]
;; ----
(defontology o)

;; => #<OWLClassImpl <8d9d3120-d374-4ffb-99d8-ffd93a7d5fdd#o#A>>
(defclass A
  :ontology o)
;; ----

;; * Generates a random UUID
;; ** Not good practice but supported by OWL API
;; * We use the `:ontology` frame

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; If we do nothing else, tawny identifies an ontology using a random
;; Universally Unique IDentifier (UUID). This is not entirely best
;; practice and, indeed, is illegal for some serialisation
;; formats. However, the OWL API supports it, and it's useful where we are
;; playing or giving a demo.

;; If we define a class, we use the ontology IRI to form the entity IRI, just
;; adding the symbol name as the fragment. Note in this case I have used the
;; `:ontology` frame as we have multiple ontologies in one file. This is the
;; safe as otherwise the results depend on the order of evaluation.

;; To see the IRI, type "A" into the REPL after evaluating the two forms above.
;; The UUID is random, so it will be different each time!

;; The "<<OWLClassImpl>>" stuff comes from the OWL API, and is just the
;; "toString" method. It's not great and at some point I will fix this.
;; ====
;; endif::backend-slidy[]

;; == Symbols and IRIs

;; * We should identify our ontologies correctly
;; * We use the `:iri` frame for our second ontology
;; * Again, the class uses the symbol name as the fragment

;; [source,lisp]
;; ----
(defontology i
   :iri "http://www.w3id.org/ontolink/example/i")

;; => #<OWLClassImpl <http://www.w3id.org/ontolink/example/i#B>>
(defclass B
  :ontology i)
;; ----

;; == Symbols and IRIs

;; * The relationship is programmatically defined
;; * We can change it to whatever we want
;; * Using the `:iri-gen` frame to supply a function
;; * Here we reverse the symbol name
;; * We call the symbol name: "the tawny name"

;; [source,lisp]
;; ----
(defontology r
  :iri "http://www.w3id.org/ontolink/example/r"
  :iri-gen (fn [ont name]
             (iri (str (as-iri ont)
                       "#"
                       (s/reverse name)))))

;; => #<OWLClassImpl <http://www.w3id.org/ontolink/example/r#EDC>>
(defclass CDE
  :ontology r)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; This is a pretty pointless transformation!
;; ====
;; endif::backend-slidy[]

;; == OBO Identifiers

;; * OBO identifiers present a challenge
;; * Source code is the ultimate in WYSIWYG
;; * Use the underlying identifiers and display something different to
;; the user

;; [source,notlisp]
;; ----
;; (defclass GO:00004324
;;   :super (owl-some RO:0000013 GO:00003143)
;;   :annotation (annotation IAO:0504303 "Transporters are..."))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Where would this programmatic transformation be useful?

;; With Protege or another GUI, we can use the underlying identifiers
;; and display something different to the user. With source code, we
;; cannot. Clearly something like the example above is just not
;; acceptable (although it is actually legal tawny code or lisp).
;; ====
;; endif::backend-slidy[]

;; == OBO Identifiers

;; * Tawny-OWL provides an OBO style ID iri-gen function.
;; * We set that here

;; [source,lisp]
;; ----
(defontology obo
  :iri "http://www.w3id.org/ontolink/example/obo"
  :iri-gen tawny.obo/obo-iri-generate)

(tawny.obo/obo-restore-iri obo "./src/tawny/tutorial/whats_in_a_name.edn")
;; ----

;; == OBO Identifiers

;; * Now we can evaluate these forms
;; * Each gets a numeric identifier, OBO style
;; * The identifier is *stable*

;; [source,lisp]
;; ----
;; => #<OWLClassImpl <http://purl.obolibrary.org/obo/EXAM_000003>>
(defclass F
  :ontology obo)

;; => #<OWLClassImpl <http://purl.obolibrary.org/obo/EXAM_000002>>
(defclass G
  :ontology obo)

;; => #<OWLObjectPropertyImpl <http://purl.obolibrary.org/obo/EXAM_000001>>
(defoproperty ro
  :ontology obo)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; In this case, we have totally dissociated the IRI from the
;; symbol. The IRI is not auto-generated here -- it is stable, and
;; will come out the same every time and on every machine. You should
;; get the same IRIs
;; exactly.
;; ====
;; endif::backend-slidy[]

;; == Task {task}: Conclusions

;; * Can generate random UUID IRIs for ease
;; * Mostly, we use the symbol (variable) name as fragment
;; * Relationship is programmatic
;; * OBO IDs are a pain in source
;; * Tawny-OWL supports them

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; We can generate random UUID IRIs for ease

;; Mostly, we use the symbol name as fragment

;; The relationship between IRIs and symbols is programmatic and can
;; be independent

;; OBO IDs are (not meaningful and) a pain in source

;; But Tawny supports them by substituting the numeric IDs to more
;; meaningful names to aid the user

;; ====
;; endif::backend-slidy[]
