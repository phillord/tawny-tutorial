;; == Task {counter:task}: Patternising

;; * Make full use of an existing pattern from Tawny


;; == Patternising

;; * We still have a lot of typing
;; * The value partition has lots of bits
;; * Easy to get wrong (`Polarity` was wrong)
;; * Tawny supports this pattern directly

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; I missed half the axioms of `Polarity` in the last set of examples. Believe me
;; or not, but I did not do this deliberately, it was a genuine mistake; as
;; should be obvious from the existence of this note, I did leave it in
;; deliberately once I discovered it!
;; ====
;; endif::backend-slidy[]


;; == Namespace

;; * The value partition pattern is found in `tawny.pattern`
;; * We `use` it here

;; [source,lisp]
;; ----
(ns tawny.tutorial.amino-acid-pattern
  (:use [tawny owl pattern]))
;; ----

;; == And the preamble

;; * This is the same as before

;; [source,lisp]
;; ----
(defontology aa)

(defclass AminoAcid)
;; ----

;; == The size value partition

;; * This is a new form
;; * Syntactically similar to what we seen before
;; * `defpartition` defines that we will have a partition
;; * `[Tiny Small Large]` are the values
;; * `hasSize` is implicit -- it will be created

;; [source,lisp]
;; ----
(defpartition Size
  [Tiny Small Large]
  :domain AminoAcid)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; This is syntactically similar because it's just a new function defined in the
;; language. We are adding nothing clever here just using a language as it is
;; intended to be used.
;; ====
;; endif::backend-slidy[]


;; == The size value partition

;; * And (some of) the OMN.

;; [source,omn]
;; ----
;; ObjectProperty: aa:hasSize
;;     Domain:
;;         aa:AminoAcid
;;
;;     Range:
;;         aa:Size
;;
;;     Characteristics:
;;         Functional


;; Class: aa:Size
;;     EquivalentTo:
;;         aa:Large or aa:Small or aa:Tiny
;; ----
;;
;; == More value partitions

;; * Adding partitions for all the properties is easy.


;; [source,lisp]
;; ----
(defpartition Charge
  [Positive Neutral Negative]
  :domain AminoAcid)

(defpartition Hydrophobicity
  [Hydrophobic Hydrophilic]
  :domain AminoAcid)

(defpartition Polarity
  [Polar NonPolar]
  :domain AminoAcid)

(defpartition SideChainStructure
  [Aromatic Aliphatic]
  :domain AminoAcid)
;; ----

;; == Using these partitions

;; * `defpartition` also applies the `as-facet` function
;; * So, we can use `facet` also
;; * Syntactically, the ontology has simplfied
;; * Same semantics underneath

;; [source,lisp]
;; ----
(as-subclasses
 AminoAcid

 (defclass Alanine
   :super (facet Neutral Hydrophobic NonPolar Aliphatic Tiny))

 (defclass Arginine
   :super (facet Positive Hydrophilic Polar Aliphatic Large))

 (defclass Asparagine
   :super (facet Neutral Hydrophilic Polar Aliphatic Small))

 ;; and the rest
 )
;; ----

;; == Task {task}: Conclusions

;; * Tawny directly supports the value partition
;; * This integrates with facets
;; * Together can simply this (very common!) form of ontology
