;; == Tawny-OWL Key Features

;; * Ontology building tool
;; * Unprogrammatic syntax
;; * Evaluative
;; * Broadcasting
;; * Patternised
;; * Fully Extensible
;; * Integrated Reasoning
;; * Built on commodity language
;; * Access to fully programming Toolchain

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Next, we move onto a formal walk-through of tawny-owl features. In
;; this section I do not intend to describe all of the features in
;; detail, but to give an overview, so that you will know what is
;; coming up.

;; This is a literately programmed document, so we start with a
;; namespace, but I have hidden this from the slides because we do not
;; need so much baggage so early on. It will be introduced in detail
;; later.

;; ====

;; [source,lisp]
;; ----
(ns tawny.tutorial.features
  (:use [tawny owl reasoner]))
;; ----

;; endif::backend-slidy[]


;; == Ontology Building Tool

;; * Tawny-OWL is an Ontology Building Tool
;; * A "Textual User Interface"
;; * Usable as an API
;; * But not designed as an API
;; * Built with the users in mind

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; First feature : Tawny is an ontology building tool

;; Unlike Protege (which has a GUI), Tawny-OWL has a textual user
;; interface; changes to the ontology are made through the
;; application of Clojure forms

;; For those of you from a functional programming background, tawny is
;; not very functional.

;; It is unusable as an API for manipulating ontologies, but was not
;; really designed for that purpose but built with the users in mind;
;; features have been implemented to help the users build the ontology
;; quickly and simply as possible.

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

;; Second feature : Tawny has an unprogrammatic syntax

;; The problem with exiting programmatic solutions to building
;; ontologies (e.g. the OWL API and Brain) is that is carries a lot of
;; Java baggage.

;; So, when using these you can never truly forget that you are
;; programming

;; Thus we have aimed (as far as possible) to make tawny simple to
;; define simple ontologies.

;; Here, this statement defines a new ontology. Of course,
;; the choice of programming language that we have chosen has
;; implications and the parenthesis is the most obvious one to anyone
;; from a lisp background.

;; ====
;; endif::backend-slidy[]


;; == Unprogrammatic Syntax

;; * Many OWL syntaxes to choose from
;; * Functional, Concrete, XML, RDF
;; * Manchester syntax (OMN) is designed for typing
;; * Frame-based, rather than axiom-based
;; ** i.e. all information about an entity is grouped
;; ** Entity-Frame-Value

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

;; Creating a new syntax for ontologies seemed unnecessary as there
;; are really far too many of these already e.g. Functional, XML or
;; RDF

;; Thus it was decided that Tawny model an existing syntax.

;; But which syntax?

;; One which was built for the specific use case of typing was
;; Manchester syntax (also known as "OWL Manchester Notation" or
;; OMN). It is a relatively clean syntax, and can be used to define
;; new classes easily.

;; Manchester syntax is frame-based which means that all information
;; about an ntity is grouped into a single construct as shown in the
;; example.

;; Generally a frame-based syntax follows the format:
;; Entity-Frame-Value(s)

;; ====
;; endif::backend-slidy[]


;; == Unprogrammatic Syntax

;; * Tawny-OWL modelled on OMN
;; * Modified to use Clojure/Lisp syntax

;; [source,lisp]
;; ----
(defclass A
  :label "A"
  :comment "A is a kind of thing.")
;; ----

;; * Entities need parenthesis
;; * No longer need commas
;; * Blocks are explicit, so easier to parse

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Thus Tawny is modelled on Manchester Syntax...

;; ...but modified to use valid Clojure/Lisp syntax

;; This is the equivalent class definition for class A in
;; Tawny (recall the OMN definition shown on the previous slide)

;; Some things are easier, and some are harder
;; For example:
;; - entities need parenthesis
;; - no longer need the commas between values
;; - blocks are explicit which means it is easier to parse

;; ====
;; endif::backend-slidy[]


;; == Unprogrammatic Syntax

;; * Frame name usage i.e. `:frame-name` and not `frame-name:`
;; ** Just for fit with Clojure
;; * Some frame name changes e.g. `:super` rather that `SubClassOf:`
;; ** More information at http://www.russet.org.uk/blog/2985
;; ** Consistent with property i.e. `:super` not `SubPropertyOf:`
;; * Some new "convenience" frames
;; ** `:label` and `:comment`
;; ** `:sub` meaning ontologies can be built bottom-up or top-down
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

;; These are not the only differences between Manchester syntax and
;; Tawny-OWL.

;; Usage of frame names has changed so its :frame-name rather than
;; frame-name:. This was so that we could use Clojure keywords as
;; frame names.

;; Some of the frame names have changed. For example :super rather
;; than SubClassOf:. For more information on this see Phil's blog.

;; For the sake of consistency the same keyword can be used with
;; properties

;; Being a programming language rather than a format it is relatively
;; easy to add new features with a clearly defined semantics.

;; So, for example:
;; - :label and :comment -- expand to relevant annotation axioms
;; - :sub keyword so that ontologies can be built bottom-up. In
;;   practice, so far this has not really used. However this ensures
;;   that the syntax does not dictate the ontology development style.

;; Together these provide an easy way of creating of new entities
;; as shown in the class definition of B.

;; ====
;; endif::backend-slidy[]


;; == Unprogrammatic Syntax

;; * Two Comment syntaxes
;; * Explicit creation of new entities
;; ** Less error-prone
;; ** Define before use optional
;; * General Concept Inclusion (GCI) fully supported
;; * Same syntax for patterns

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; There are some other differences. Firstly, tawny has (two) comment
;; syntaxes. OMN is commentable too but the parse doesn't always work.

;; The other major one is the use of an "explicit definition"
;; semantics. Classes must be defined before they are used by other
;; classes. This is a semantics shared with Brain, and was chosen
;; deliberately as it was too easy to make typo's without.

;; Though it is optional through the use of Strings.

;; General Concept Inclusion (GCI) is fully supported which OMN
;; doesn't do.

;; We can also build patterns in the same syntax and files as the
;; ontology. You will see many examples of this through the tutorial.

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

;; Feature 3 : Evaluative

;; We can type, change and add new entities and reprogram things as we
;; go. For the non-programmers this is likely to be so obvious that
;; you cannot see why I am describing it, but for programmers from a
;; Java background it is hard to under-estimate what a massive
;; difference this makes to development styles.

;; There is no compile cycle -- you can change things as you go, and
;; you do not need to continually restart your application.

;; This is not entirely true, you do need to restart, but not that
;; often.

;; Massive time saver

;; ====
;; endif::backend-slidy[]


;; == Compiled

;; * There is a compile cycle
;; * But you won't notice it
;; * Tawny-OWL is performant
;; * Tawnyised version of GO loads ~1 min

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Actually, there is a compile cycle. But you won't notice it.

;; So those of you from a programming background, may be
;; thinking "hmm, an interpreted language running on top of
;; JVM. Actually, Clojure statements are compiled to bytecode
;; on-the-fly the run directly on the JVM (which in turn will JIT
;; compile them). So, it's fast enough.

;; Main thing here is that Tawny is performant, in fact the tawnyised
;; version of GO loads in about a minute.

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

;; Feature 4 : Broadcasting

;; Broadcasting is a really very handy feature from R. You do not have to
;; explicitly deal with the lists and numbers (ontology entities in the
;; case of tawny).

;; In this R example, we are adding four to all the elements of the
;; list.

;; ====
;; endif::backend-slidy[]


;; == Broadcasting

;; * Tawny-OWL does something similar
;; * `owl-some` expands to *two* existential restrictions

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

;; Tawny does something similar

;; One statement in tawny expands to two in OMN. Two calls to the OWL
;; API also. It is also one of those things that makes tawny less like
;; an API and more like a TUI. Although this is reasonably
;; efficiently implemented, it does have a performance cost -- more
;; than made up for in saved typing for ontology developers.

;; ====
;; endif::backend-slidy[]


;; == Patternised

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

;; Feature 5 : Patternised

;; This is the first example of a pattern that we see (although
;; broadcasting is a pattern also, in a sense). This is
;; the "some-only" pattern which is so common, it often not seen as a
;; pattern.

;; This was also the motivation for broadcasting as some-only makes
;; little sense without broadcasting, although it might not be
;; immediately obvious why this is the case.

;; In this class definition for D, the some-only statement expands
;; into three axioms; two existential and one universal.

;; ====
;; endif::backend-slidy[]


;; == Single fully extensible syntax

;; * Tawny-OWL is implemented in Clojure
;; * Tawny-OWL patterns are implemented in Clojure
;; * Tawny-OWL ontologies are written in Clojure

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

;; Feature 6 : Single fully extensible syntax

;; In theory, tawny does nothing that it is not possible to do
;; already. But the single syntax and environment is important. I can
;; easily add new syntax even for a specific ontology. Doing this
;; where half the ontology is built in protege and half outside is
;; just intractable. With a single syntax it becomes so easy that it
;; happens often and all the time.

;; ====
;; endif::backend-slidy[]


;; == Reasoned Over

;; * Tawny-OWL fully supports reasoning
;; * In this case using HermiT
;; * Based on all the examples given so far, `F` has three subclasses

;; [source,lisp]
;; ----
(defclass F
   :equivalent (owl-some r (owl-or A B)))

(subclasses F)
;; #{}

(reasoner-factory :hermit)

(isubclasses F)
;; #{C D E}
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Feature 7 : Integrated Reasoning

;; Tawny is fully supports reasoning through two maven compliant
;; reasoner; ELK and HermiT.

;; In this example we are using HermiT.

;; Based on the class definitions given so far, F has three subclasses;
;; C, D and E.

;; ====
;; endif::backend-slidy[]


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

;; Feature 8 : Commodity language

;; Most of this I am not going to show anything other than implicitly,
;; but tawny is based on a commodity language. So it has access to
;; many APIs which can do useful things for you. We have used quite a
;; few of these either in the context of programming tawny or in
;; developing OWL ontologies using tawny (in fact all
;; of those given here).

;; The key point to remember here is that programming tawny and
;; developing ontologies are not disjoint. You have the same power in
;; using tawny as we do in developing it.

;; ====
;; endif::backend-slidy[]


;; == Commodity Toolchain

;; * Editing
;; ** IDEs: Eclipse, IntelliJ, Netbeans
;; ** Power Editors: Emacs, Vim, Sublime, Cursive (via IntelliJ)
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

;; Feature 9 : Commodity Toolchain

;; Finally, we have full access to a rich Toolchain, including a wide
;; range of IDEs, power-editors or web editors, as well as some very
;; novel environments (take a look at lighttable -- implemented in
;; Clojure and supporting it first).

;; We also make extensive use of version control -- we've been using
;; git, but you can use whatever you want. You can integrate your
;; ontology development process and software development process.

;; Dependency -- someone else can host your ontology without having to
;; use their URIs!

;; Testing and Continuous Integration. Remote evaluation (actually,
;; you will use this all the time even if it seems you are not).

;; Linters. Rewriters. We have only just got started with these.

;; That is the end of the features of Tawny-OWL.

;; ====
;; endif::backend-slidy[]
