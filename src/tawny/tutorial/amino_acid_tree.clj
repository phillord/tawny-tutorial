;; == Task {counter:task}: Defining a Tree

;; * For the next section, we build an amino acid ontology
;; * We do this in several ways
;; * First, we start with a simple hierarchy

;; == Amino Acids

;; * Chemical Molecules
;; * Central Carbon, with an amino and acid group
;; * And a "side chain" or "R" group which defines the differences
;; * Have a number of chemical properties
;; * There are 20


;; == A namespace

;; * You can should create your ontology in the file `tutorial/amino_acid_tree.clj`
;; * The full code in these slides can be found in `tutorial/amino_acid_tree_s.clj`

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; [source,lisp]
;; ----
(ns tutorial.amino-acid-tree-s
  (:use [tawny.owl]))
;; ----

;; ====
;; endif::backend-slidy[]


;; [source,notlisp]
;; ----
;; (ns tutorial.amino-acid-tree
;;   (:use [tawny.owl]))
;; ----

;; == Tree

;; * So, we define our (flat) tree

;; [source,lisp]
;; ----
(defontology aa)

(defclass AminoAcid)

(defclass Alanine
          :super AminoAcid)

(defclass Arginine
          :super AminoAcid)

(defclass Asparagine
          :super AminoAcid)

;; and the rest...
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Defining the basic tree is not too hard -- we just use the `:super` keyword.
;; If you think this involves too much typing, yes, it does.
;; ====
;; endif::backend-slidy[]

;; == Disjoint

;; * Let's make everything disjoint
;; * `Asparagine` is not the same as `Alanine` or `Arginine`
;; * With three amino-acids, this is painful and error-prone
;; * With twenty it would untenable


;; [source,lisp]
;; ----
(defclass Alanine
    :super AminoAcid
    :disjoint Arginine Asparagine)
;; ----

;; == Disjoint

;; * But there is a more serious problem
;; * Change `Arginine` to this
;; * This will now crash (probably)

;; [source,notlisp]
;; ----
;; (defclass Arginine
;;     :super AminoAcid
;;     :disjoint Alanine Asparagine)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; In theory, this should crash, but it may not if you have previously evaluated
;; (or saved in catnip) the `Asparagine` form above. This reflects one problem
;; with REPL based languages -- sometimes your source can get out of sync with
;; your REPL. If it does not crash now, it *will* crash when you restart and eval
;; again.

;; Most REPL programmers restart defensively every hour or two to guard against
;; this.

;; ====
;; endif::backend-slidy[]

;; == Explict Definition

;; * Tawny uses explicit definition
;; * Variables must be defined *before* use
;; * This is deliberate!
;; * Can be avoided by using Strings
;; * But that is error-prone

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Explicit definition is a good thing, but does hold out the possibility for
;; being a real pain. We think that it is not so in Tawny, because we have
;; features for working around it. It is possible to avoid entirely anyway, but I
;; personally do not, because it makes spelling errors all too likely. I will
;; show this once later on.
;; ====
;; endif::backend-slidy[]

;; == Simplifying the definition

;; * We can use the `as-subclasses` form
;; * Adds `AminoAcid` as super to all arguments
;; * This syntax also protects against future additions.

;; [source,lisp]
;; ----
(defclass AminoAcid)

(as-subclasses
  AminoAcid

  (defclass Alanine)
  (defclass Arginine)
  (defclass Asparagine)
  ;; and the rest...
  )
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Before we show the solution to the disjoint problem, we simplify our
;; definitions. Typing `:super AminoAcid` a lot is a pain also, so lets avoid
;; that. We introduce a new function, `as-subclasses`

;; The advantage of lexically grouping all of the subclasses in this way is also
;; that it makes the *intent* of the developer obvious. If we need to add a new
;; subclass later (unlikely in this case once finished, but during development
;; yes), then adding it next in the list *also* makes it a subclass as it
;; should be.

;; This ability to make the developer intent, and conformance to a pattern
;; explicit is a good thing!
;; ====
;; endif::backend-slidy[]

;; == And disjoint!

;; * This simplifies the disjoint behaviour also
;; * We add `:disjoint` keyword


;; [source,lisp]
;; ----
(defclass AminoAcid)

(as-subclasses
 AminoAcid
 :disjoint

 (defclass Alanine)

 (defclass Arginine)

 (defclass Asparagine)

 ;; and the rest...
 )
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Now that we have achieved this, we can also solve the disjoint problem, by
;; adding a keyword in. All the classes will now be made disjoint. If you want to
;; be sure, evaluate the source, save it and look at the OMN.
;; ====
;; endif::backend-slidy[]


;; == And, finally, covering

;; * We might also want to say
;; * These are the only amino-acids there are
;; * To do this we use a "covering" axiom
;; * Interesting ontology! Biologically true, chemically not.

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We can add a *covering* axiom in the same way. Unlike `disjoint` this is not a
;; formal part of OWL, but is a design pattern. The interesting thing about this
;; is that biologically it is true -- there are only 20 amino acids and we will
;; name them all. But to a chemist, it is demonstrably false as there are many,
;; many amino acids. How we scope the ontology and frame our competency questions
;; can very much affect the model that we build. We will show later that this
;; axiom has a very significant affect on the end model, much more significant
;; than you might presuppose at the moment.
;; ====
;; endif::backend-slidy[]


;; == And, finally, covering

;; * This is also easy to implement
;; * Here with all the amino-acids in full
;; * Again, the source code grouping is useful!

;; [source,lisp]
;; ----
(defclass AminoAcid)

(as-subclasses
 AminoAcid
 :disjoint :cover

 (defclass Alanine)
 (defclass Arginine)
 (defclass Asparagine)
 (defclass Aspartate)
 (defclass Cysteine)
 (defclass Glutamate)
 (defclass Glutamine)
 (defclass Glycine)
 (defclass Histidine)
 (defclass Isoleucine)
 (defclass Leucine)
 (defclass Lysine)
 (defclass Methionine)
 (defclass Phenylalanine)
 (defclass Proline)
 (defclass Serine)
 (defclass Threonine)
 (defclass Tryptophan)
 (defclass Tyrosine)
 (defclass Valine))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Putting in a covering axiom, then adding a new sibling and forgetting to
;; modify the covering axiom is an easy mistake to make, and can be very
;; difficult to pick up, either by eye or by testing. Tawny makes this harder.
;; ====
;; endif::backend-slidy[]

;; == And, finally, covering

;; * And, here is a subset of the equivalent OMN


;; [source,omn]
;; ----
;; Class: aa:AminoAcid
;;     EquivalentTo:
;;         aa:Alanine or aa:Arginine or aa:Asparagine
;;         or aa:Aspartate or aa:Cysteine or aa:Glutamate
;;         or aa:Glutamine or aa:Glycine or aa:Histidine
;;         or aa:Isoleucine or aa:Leucine or aa:Lysine
;;         or aa:Methionine or aa:Phenylalanine or aa:Proline
;;         or aa:Serine or aa:Threonine or aa:Tryptophan
;;         or aa:Tyrosine or aa:Valine

;; DisjointClasses:
;;     aa:Alanine, aa:Arginine, aa:Asparagine, aa:Aspartate,
;;     aa:Cysteine, aa:Glutamate, aa:Glutamine, aa:Glycine,
;;     aa:Histidine, aa:Isoleucine, aa:Leucine, aa:Lysine,
;;     aa:Methionine, aa:Phenylalanine, aa:Proline, aa:Serine,
;;     aa:Threonine, aa:Tryptophan, aa:Tyrosine, aa:Valine
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; I have not shown all the OMN because it is long and tedious, but these are the
;; keypoints added by the subclasses function.

;; If you evaluated the `:disjoint Alanine` definitions early (and they did not
;; crash!), then you will find that there are some `Disjoint:` frames on
;; individual amino acids also. These make no semantic difference and are an
;; artifact of the tutorial.
;; ====
;; endif::backend-slidy[]


;; == Task {task}: Conclusions

;; * It is easy to build simple hierarchies
;; * We can group parts of the tree
;; * There is support for disjoints
;; * There is support for covering axioms

