;; == Task 1: Defining a Tree

;; * For the next section, we build an amino acid ontology
;; * We do this in several ways
;; * First, we start with a simple hierarchy


;; == A namespace

;; * You can should create your ontology in the file `lisbon/amino_acid_tree.clj`
;; * The code in these slides in in `lisbon/amino_acid_tree_s.clj`

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; [source,lisp]
;; ----
(ns lisbon.amino-acid-tree-s
  (:use [tawny.owl]))
;; ----

;; ====
;; endif::backend-slidy[]


;; [source,notlisp]
;; ----
;; (ns lisbon.amino-acid-tree
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

;; == Disjoint

;; * Let's make everything disjoint
;; * `Asparagine` is not the same as `Alanine` or `Arginine`
;; * With three amino-acids, this is painful and error-prone
;; * With twenty it would untenable


;; [source,lisp]
;; ----
(defclass Alanine
          :disjoint Arginine Asparagine)
;; ----


;; == Disjoint

;; * But there is a more serious problem
;; * Change `Asparagine` to this
;; * This will now crash (probably)

;; [source,notlisp]
;; ----
;; (defclass Arginine
;;           :disjoint Alanine Asparagine)
;; ----

;; == Explict Definition

;; * Tawny uses explicit definition
;; * Variables must be defined *before* use
;; * This is deliberate!
;; * Can be avoided by using Strings
;; * But that is error-prone


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


;; == And, finally, covering

;; * We might also want to say
;; * These are the only amino-acids there are
;; * To do this we use a "covering" axiom
;; * Interesting ontology! Biologically true, chemically not.


;; == And, finally, covering

;; * This is also easy to implement
;; * Here with all the amino-acids in full


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


;; == Task 1: Conclusions

;; * It is easy to build simple hierarchies
;; * We can group parts of the tree
;; * There is support for disjoints
;; * There is support for covering axioms
