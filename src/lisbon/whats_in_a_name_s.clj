;; == Task {counter:task}: Understanding Names

;; * Understand how tawny uses three namespaces
;; * See how to use them independently
;; * Understand how they allow OBO ID support

;; == Background

;; * Ontology Entities need names
;; * Tawny has different requirements for names
;; * But we need to support alternative workflows
;; * So, Tawny is flexible
;; * With "sensible" defaults


;; == OWL

;; * Tawny is build on the OWL API
;; * Underneath, therefore, it is part of the web
;; * OWL uses IRIs (i.e. URIs or URLs)
;; * IRIs provide a single, shared global namespace
;; * With a (social) mechanism for uniqueness

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We have to inherit directly from the web, because all the software that we are
;; building on depends on it and/or requires it.

;; IRIs have this unusual characteristic of being global.
;; ====
;; endif::backend-slidy[]


;; ==  Symbols

;; * Tawny uses symbols to identify entities
;; * Easy to type (`A` rather than `"A"`)
;; * Provides define-before-use semantics
;; * Comes directly from Clojure
;; * Supported in the IDEs

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Symbols are a core feature of Clojure and, indeed, any lisp. They are kind of
;; equivalent to variable names in other programming languages, but not exactly
;; the same since lisp gives you flexibility to handle them directly.

;; Using symbols also provides a set of other advantages -- in addition to the
;; define-before-use semantics, they are normally syntax highlighted specially by
;; editors and, rather usefully, they will normally auto-complete
;; ("code-complete" or "intellisense") in an IDE. Very useful for big ontologies.

;; ====
;; endif::backend-slidy[]

;; == Namespace

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; [source,lisp]
;; ----
(ns lisbon.whats-in-a-name-s
  (:use [tawny owl])
  (:require [tawny.obo])
  (:require [clojure.string :as s]))
;; ----


;; ====
;; endif::backend-slidy[]


;; * We will use `tawny.obo` later to show numeric IDs
;; * `clojure.string` is for string manipulation.

;; [source,notlisp]
;; ----
;; (ns lisbon.whats-in-a-name
;;   (:use [tawny owl obo])
;;   (:require [clojure.string :as s))
;; ----


;; == Symbols and IRIs

;; * What is the relationship between symbols and IRIs?
;; * In tawny, this is a per-ontology setting
;; * We use the `:ontology` frame
;; * By default the symbol forms the fragment of the IRI

;; [source,lisp]
;; ----
(defontology o)

;; => #<OWLClassImpl <8d9d3120-d374-4ffb-99d8-ffd93a7d5fdd#o#A>>
(defclass A
  :ontology o)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; If we do nothing else, tawny identifies an ontology using a random UUID. This
;; is not entirely best practice and, indeed, is illegal for some serialisation
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
;; * We can change it to what ever we want
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

;; == The iri-gen function

;; [source,notlisp]
;; ----
;; (fn [ont name]          ;; <1>
;;   (iri                  ;; <2>
;;    (str                 ;; <3>
;;     (as-iri ont)        ;; <4>
;;     "#"                 ;; <5>
;;     (s/reverse name)))) ;; <6>
;; ----

;; This is the first function we have seen so, we go through it in detail

;; <1> Create an anonymous function, with parameters `ont` and `name`
;; <2> Create an IRI object from the string
;; <3> Concatentate all arguments
;; <4> Get the Ontology IRI
;; <5> "#"
;; <6> Reverse the name passed in!

;; == OBO identifiers

;; * OBO identifiers present a challenge
;; * Source code is the ultimate in WYSIWYG

;; [source,notlisp]
;; ----
;; (defclass GO:00004324
;;   :super (owl-some RO:0000013 GO:00003143)
;;   :annotation (annotation IAO:0504303 "Transporters are..."))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; With protege or another GUI, we can use the underlying identifiers and display
;; something different to the user. With source code, we cannot. Clearly
;; something like the example above is just not acceptible (although it is
;; actually legal tawny code or lisp).
;; ====
;; endif::backend-slidy[]

;; == OBO Identifiers

;; * Tawny provides an OBO style ID iri-gen function.
;; * We set that here

;; [source,lisp]
;; ----
(defontology obo
  :iri "http://www.w3id.org/ontolink/example/obo"
  :iri-gen tawny.obo/obo-iri-generate)
;; ----

;; * Will explain this bit later!

;; [source,lisp]
;; ----
(tawny.obo/obo-restore-iri obo "./src/lisbon/whats_in_a_name.edn")
;; ----

;; == OBO Identifiers

;; * Now we can eval these forms
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
;; In this case, we have totally dissociated the IRI from the symbol. The IRI is
;; not auto-generated here -- it is stable, and will come out the same every time
;; and on every machine. You should get the same IRIs exactly.
;; ====
;; endif::backend-slidy[]


;; == OBO Identifiers

;; * This one does not!

;; [source,lisp]
;; ----
;; => #<OWLClassImpl <http://purl.org/ontolink/preiri/#4b463bc1-414b-4730-89a3-7ff72902c744>>
(defclass H
  :ontology obo)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Interestingly, when we get to here, we get a strange ID with a UUID for
;; fragment and an IRI that it unrelated to anything.
;; ====
;; endif::backend-slidy[]


;; == How OBO Identifiers work

;; * The mapping is stored in a file
;; * The `obo-restore-iri` line above reads this file
;; * If a symbol has no mapping, we use the "pre-iri" form.


;; == How OBO Identifiers work

;; * The mapping file is generated
;; * Human readable, and line-orientated
;; * Deterministically ordered
;; * Will version!
;; * Uses EDN format.

;; [source,notlisp]
;; ----
;; ("ro"
;;  "http://purl.obolibrary.org/obo/EXAM_000001"
;;  "G"
;;  "http://purl.obolibrary.org/obo/EXAM_000002"
;;  "F"
;;  "http://purl.obolibrary.org/obo/EXAM_000003")
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; The mapping file has been designed to work with version control, because it
;; needs to be shared between all developers. Although it is a generated file, it
;; is source code, since it cannot be recreated from fresh (not in the same order
;; anyway).

;; EDN format is a Clojure thing. It's basically a Clojure read syntax.
;; ====
;; endif::backend-slidy[]

;; == How OBO identifiers

;; * Stable pre-iri's
;; * No need for a server such as URIgen

;; [source,lisp]
;; ----
;; this stores any new IDs we have created
(comment
  (tawny.obo/obo-store-iri obo "./src/lisbon/whats_in_a_name.edn"))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; While the preiris are automatically created, if we choose they can be made
;; stable by simply saving them into the file with the form above. This can be
;; safely done every time the file is evaluated, because the order is
;; deterministic, so it will cause no false diffs in versioning.

;; This is potentially useful if you are collaborating with others and want to
;; co-ordinate at pre-release time. It's not essential if others are using
;; tawny -- there is no need, since classes can be refered to by symbol.

;; There is a potential disadvantage. This creates an IRI (and entry in the file)
;; for every new entity created. Not a problem with protege, but tawny is fully
;; programmatic. I can create 10^6 new classes in one line of code.

;; pre-iris all appear at the end of the EDN file!
;; ====
;; endif::backend-slidy[]

;; == How OBO Identifers work

;; * How to create permanent IDs
;; * Needs to be co-ordinated, since IDs are incremental
;; * Use version-control to co-ordinate
;; * One person, or as part of a release process

;; [source,lisp]
;; ----
;; this coins permanent IDS, in a controlled process!
(comment
  (tawny.obo/obo-generate-permanent-iri
   "./src/lisbon/whats_in_a_name.edn"
   "http://purl.obolibrary.org/obo/EXAM_"))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; At some point, you need to coin new IDs that will become permanent. This has
;; to happen in a co-ordinated fashion. It could be done as part of release. Or
;; by bot during continuous integration.
;; ====
;; endif::backend-slidy[]


;; == How OBO Identifiers work

;; * I think having no server is nice
;; * Reusing version control makes sense
;; * It's programmatic! You are free to disagree.

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; How well would this workflow work in practice? Not sure. It would work for a
;; small number of developers. There are many tweaks that could be made for
;; different scales -- saving to multiple files, pre-iris in one place, perms in
;; another. No pre-iris at all. Use URIGen. Manually coin permanent IRIs as part
;; of quality control.

;; It's programmatic and easy to change.
;; ====
;; endif::backend-slidy[]

;; == Tawny Names

;; * It is possible not to use symbols
;; * The `iri-gen` function takes a string not a symbol!
;; * This string is the tawny name
;; * Consider the following


;; [source,lisp]
;; ----
;; String building!
(defontology s)

(owl-class "J" :ontology s)
(object-property "r" :ontology s)
(owl-class "K"
           :ontology s
           :super (owl-some "r" "J"))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We can do without symbols and instead use just strings. Note that we also have
;; to switch functions `owl-class` instead of `defclass`.
;; ====
;; endif::backend-slidy[]


;; == Tawny Name

;; * These forms do *NOT* define symbols
;; * This *WILL NOT* work
;; * Neither `r` nor `J` have been defined

;; [source,lisp]
;; ----
(comment
  (owl-class "L"
             :ontology s
             :super (owl-some r J)))
;; ----

;; == Tawny Name

;; * Danger!
;; * Consider this statement.
;; * But we did not define "L"
;; * But we have used it.

;; [source,lisp]
;; ----
(owl-class "M"
           :ontology s
           :super "L")
;; ----

;; * And so, it becomes defined

;; [source,omn]
;; ----
;; Class: s:M
;;     SubClassOf:
;;         s:L

;; Class: s:L
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; The use of strings means that we can define things without the tawny-name
;; being in "primary position". It just happens. You need to be careful.
;; ====
;; endif::backend-slidy[]


;; == Why use strings?

;; * Partly, there for implementation
;; * But made public as string manipulation is easier
;; * Most useful for development

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; The main reason that I have left this in place is for use as an API. Clojure
;; allows full manipulation of symbols like most lisps, but it's a bit of a pain.
;; It's not possible, for example, to concatenate two symbols to make a longer
;; one (or rather, they need to be converted to strings, then concat'd then
;; converted back again). And the creation and interning of new symbols as
;; variables requires the use of macros rather than normal functions.

;; Having said that, tawny does offer some facilities to help with this process

;;  - `tawny.owl/intern-owl-entity`
;;  - `tawny.owl/intern-owl-string`
;;  - `tawny.owl/defentity`
;;  - `tawny.owl/intern-owl-entities`
;;  - `tawny.util/quote-word`
;;  - `tawny.util/name-tree`

;; We will see a few of these later.
;; ====
;; endif::backend-slidy[]

;; == Summary

;; * The relationships are summarized as follows
;; * We will look at the arrow on the right next!

;; image:tawny-name.png[]


;; == Task {task}: Conclusions

;; * There are three namespaces in use
;; ** tawny-name
;; ** clojure symbols
;; ** IRIs
;; * The relationship between the three is fluid
;; * Generally, just use symbols!
