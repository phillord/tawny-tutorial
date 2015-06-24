

;; == Tawny-OWL Key Features

;; * Now we move onto Tawny-OWL features
;; * Ontology building tool
;; * Unprogrammatic syntax, minimal baggage
;; * Evaluative
;; * Broadcasting
;; * Patternized
;; * Fully Extensible
;; * Integrated Reasoning
;; * Build on commondity language
;; * Access to fully programming Tool Chain


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Next, we move onto a formal walk-through of tawny-owl features. In this
;; section I do not intend to describe all of the features in detail, but to give
;; an overview, so that you will know what is coming up.


;; This is a literately programmed document, so we start with a namespace, but I
;; have hidden this from the slides because we do not need so much baggage so
;; early on. It will be introduced in detail later.

;; ====


;; [source,lisp]
;; ----
(ns lisbon.features
  (:use [tawny owl reasoner]))
;; ----

;; endif::backend-slidy[]

;; == Ontology Building Tool

;; * Tawny-OWL is an Ontology Building Tool
;; * "A Textual User Interface"
;; * Usable as an API.
;; * But not designed as an API

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; For those of you from a functional programming background, tawny is not very
;; functional. It is usuable as an API for manipulating ontologies, but was not
;; really designed for that purpose. Still, it is no worse for this than the
;; OWL API.

;; ====
;; endif::backend-slidy[]


;; == Unprogrammatic Syntax

;; * Both OWL API and Brain carry Java baggage
;; * You can never forget you are programming
;; * Tawny-OWL aimed to avoid this


;; [source,lisp]
;; ----
(defontology o)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; We have aimed as far as possible to make tawny simple to define simple
;; ontologies. So this statement for instead defines a new ontology. Of course,
;; the choice of programming language that we have chosen has implications and
;; the parenthesis is the most obvious one to anyone from a lisp background.

;; ====
;; endif::backend-slidy[]



;; == Unprogrammatic Syntax

;; * Are many OWL syntaxes to chose from
;; * Functional, Concrete, XML.
;; * Or RDF (RDF/XML, Turtle, N3)
;; * Manchester (OMN) syntax designed for typing
;; * Frame based, rather than axiom based

;; [source,omn]
;; ----
;; Class: o:A

;;     Annotations: 
;;         rdfs:label "A"@en,
;;         rdfs:comment "A is a kind of thing."@en
;; ----


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; I did not want to create my own syntax because there are really far too many
;; of these already. One which was built for the specific use case of typing was
;; Manchester syntax (also known as "OWL Manchester Notation" or OMN). It is a
;; relatively clean syntax, and can be used to define new classes easily.

;; In my very early work on the karyotype ontology, I even considered writing an
;; OMN environment, but OMN is not that easy to deal with programmatically, so I
;; dropped the idea.

;; ====
;; endif::backend-slidy[]


;; == Unprogrammatic Syntax

;; * Tawny-OWL modelled on OMN
;; * Modified to use lisp/clojure syntax
;; * Entities need parens
;; * No longer need commas
;; * Blocks are explicit, so easier to parse

;; [source,lisp]
;; ----
;; (defclass A
;;   :label "A"
;;   :comment "A is a kind of thing.")
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; This is the equivalent in tawny-owl. Some things are easier, and some are
;; harder.
;; ====
;; endif::backend-slidy[]

;; == Unprogrammatic Syntax

;; * Frame names use `:colon` and not `colon:`
;; * Just for fit with lisp
;; * Some names have changed `:super` rather that `SubClassOf:`
;; ** Consistent with property (`:super` not `:SubPropertyOf`)
;; ** omn is wrong anyway
;; * Some new "convenience" frames
;; * And `sub` meaning ontologies can be built bottom up or Top-down.
;; * Easy creation of new entities

;; [source,lisp]
;; ----
(defclass B
  :super A
  :label "B")
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Being a programming language rather than a format it is relatively easy to add
;; new features with a clearly defined semantics. So, for example, I wanted to
;; add a "sub" keyword so that I could build ontologeis bottom up. In practice,
;; so far I have not really used this, but I do not feel that the syntax should
;; dictate the ontology development style.

;; ====
;; endif::backend-slidy[]

;; == Unprogrammatic Syntax

;; * Other Differences from OMN:
;; ** Two Comment syntaxes
;; ** Explicit creation of new entities
;; ** Define before use optional

;; * Same syntax for patterns
;; * GCI fully supported
;; * The parser works

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; There are some other differences. Firstly, tawny has (two) comment syntaxes.
;; OMN is commentable too but the parse doesn't always work.

;; The other major one is the use of an "explicit definition" semantics. Classes
;; must be defined before they are used by other classes. This is a semantics
;; shared with Brain, and was chosen deliberately. My early experience with OMN
;; showed that it was too easy to make typo's without.

;; It is possible to avoid this if you wish.

;; We can also build patterns in the same syntax and files as the ontology. You
;; will see many examples of this through the tutorial.

;; ====
;; endif::backend-slidy[]


;; == Evaluative

;; * Tawny-OWL is "evaluative"
;; * Add new classes, new properties, new frames on-the-fly
;; * Redefine patterns
;; * Add new tests, and rerun
;; * There is no compile cycle

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; We can type, change and add new entities and reprogram things as we go. For
;; the non-programmers this is likely to be so obvious that you cannot see why I
;; am describing it, but for programmers from a Java background it is hard to
;; under-estimate what a massive difference this makes to development styles.

;; There is no compile cycle -- you can change things as you go, and you do not
;; need to continually restart your application.

;; This is not entirely true, you do need to restart, but not that often.

;; ====
;; endif::backend-slidy[]


;; == Compiled

;; * There is a compile cycle
;; * But you won't notice it
;; * Except that Tawny is performant
;; * Tawnyized version of GO loads ~1 min.


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Actually, there is a compile cycle. So those of you from a programming
;; background, may be thinking "hmm, an interpreted language running on top of
;; a JVM. Actually, clojure statements are compiled to bytecode on-the-fly them
;; run directly on the JVM (which in turn will JIT compile them). So, it's fast
;; enough.

;; ====
;; endif::backend-slidy[]

;; == Broadcasting

;; * An idea borrowed from R
;; * R is very flexible with numbers and lists
;; * Add number to list, adds the number of every element of the list


;; [source,slang]
;; ----
;; > c(1,2,3) + 4
;; [1] 5 6 7
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Broadcasting is a really very handy feature from R. You do not have to
;; explictly deal with the lists and numbers (ontology entities in the case of
;; tawny).

;; ====
;; endif::backend-slidy[]

;; == Broadcasting

;; * Tawny-OWL does something similar

;; [source,lisp]
;; ----
(defoproperty r)
(defclass C
  :super (owl-some r A B))
;; ----

;; * `C some r A`
;; * `C some r B`

;; [source,omn]
;; ----
;; Class: o:C
;;     SubClassOf: 
;;         o:r some o:B,
;;         o:r some o:A
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; One statement in tawny unwinds to two in OMN. Two calls to the OWL API also.
;; It is also one of those things that makes tawny less like an API and more like
;; a textual UI. Although this is reasonably efficiently implemented, it does
;; have a performance cost -- more than made up for in saved typing for ontology
;; developers.


;; ====
;; endif::backend-slidy[]


;; == Patternized

;; * Tawny-OWL allows patterns
;; * Broadcasting works naturally with patterns
;; * `some-only` the most common

;; [source,lisp]
;; ----
(defclass D
  :super (some-only r A B))
;; ----

;; * Expands into three (or `n+2`) axioms

;; [source,omn]
;; ----
;; Class: o:D

;;     SubClassOf: 
;;         o:r some o:A,
;;         o:r some o:B,
;;         o:r only 
;;             (o:A or o:B)
;; ----    


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; This is the first example of a pattern that we see (although broadcasting is a
;; pattern also, in a sense. This is the "some-only" pattern which is so common,
;; it often not seen as a pattern.

;; This was also the motivation for broadcasting as some-only makes little sense
;; without broadcasting, although it might not be immediately obvious why this is
;; the case.
;; ====
;; endif::backend-slidy[]

;; == Single syntax Extensible

;; * Tawny-OWL is implemented in Clojure
;; * Tawny-OWL patterns are implemented in Clojure
;; * Tawny-OWL Ontologies are written in Clojure

;; * Therefore, adding new patterns is trivial 
;; * Here we introduce two new patterns and use them

;; [source,lisp]
;; ----
(defn and-not [a b]
  (owl-and a (owl-not b)))

(defn some-and-not [r a b]
  (owl-some r (and-not a b)))

(defclass E
  :super (some-and-not r A B))
;; ----

;; * Which gives

;; [source,omn]
;; ----
;; Class: o:E
;;     SubClassOf:
;;         o:r some
;;             (o:A or (not (o:B)))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; In theory, tawny does nothing that it is not possible to do already. But the
;; single syntax and environment is important. I can easily add new syntax even
;; for a specific ontology. Doing this where half the ontology is build in
;; protege and half outside is just intractable. With a single syntax it becomes
;; so easy that it happens often and all the time.
;; ====
;; endif::backend-slidy[]


;; == Reasoned Over

;; * Tawny fully supports reasoning
;; * In this case using hermit
;; * Based on all the examples given so far, `F` has three subclasses

;; [source,lisp]
;; ----
(defclass F
   :equivalent (owl-some r (owl-or A B)))

;; #{}
(subclasses F)

(reasoner-factory :hermit)

;; #{C D E}
(isubclasses F)
;; ----


;; == Commodity Language

;; * Built on Commodity Language
;; * Full access to all APIs
;; ** Serialisation
;; ** Spreadsheet reading
;; ** Database access
;; ** Networking
;; ** Logic Programming
;; ** Test Library
;; ** Statistics and Plotting
;; ** Benchmarking

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Most of this I am not going to show anything other than implicitly, but tawny
;; is based on a commodity language. So it access to many APIs which can do
;; useful things for you. We have used quite a few of these either in the context
;; of programming tawny or in developing OWL ontologies using tawny (in fact all
;; of those given here).

;; The key point to remember here is that programming tawny and developing
;; ontologies are not disjoint. You have the same power in using tawny as we do
;; in developing it.

;; ====
;; endif::backend-slidy[]

;; == Commodity Toolchain

;; * Editing
;; ** IDEs: Eclipse, IntelliJ, Netbeans
;; ** Power Editors: Emacs, Vim, Sublime
;; ** Web Editors: Catnip, GorrilaRepl
;; ** Novel: LightTable

;; * Version Control: Any

;; * Build and Dependency
;; ** Lein, Maven or Boot

;; * Testing
;; ** Travis-CI, or any CI environment

;; * Linters, Rewriters, Remote Evaluation (nREPL)


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Finally, we have full access to a rich tool chain, including a wide range of
;; IDEs, power-editors or web editors, as well as some very novel environments
;; (take a look at lighttable -- implemented in Clojure and supporting it first).

;; We also make extensive use of version control -- we've been using git, but you
;; can use what ever you want. You can integrate your ontology development
;; process and software development process.

;; Dependency -- we'll see later how to access ontologies using the maven
;; dependency management system. Some one else can host your ontology without
;; having to use their URIs!

;; Testing, continuous integration. Remote evaluation (actually, you will use
;; this all the time even if it seems you are not).

;; Linters. Rewriters. We have only just got started with these.
;; ====
;; endif::backend-slidy[]
