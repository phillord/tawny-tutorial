;; == Task {counter:task}: Patternising

;; * Make full use of an existing pattern from Tawny-OWL

;; == The problem

;; * Used value partition to define our properties
;; * Issues with defining the value partition:
;; ** We still have a lot of typing
;; ** The value partition has lots of parts
;; ** Easy to get wrong (e.g. `Polarity`)
;; * How can we simplify and ensure consistency?
;; * Tawny-OWL supports this pattern directly

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; In the previous task we used the value partition to define our
;; amino acid properties.

;; Useful but still have a lot of typing to do; it takes at
;; least 10 lines of code to define one value partition and is made up
;; of many parts.

;; Which means it is easy to get wrong. For example did any one
;; notice that the Polarity value partition was incomplete. We are
;; missing the domain and range restrictions for hasPolarity in the
;; last set of examples. Originally not a deliberate but genuine
;; mistake; but once noticed it was decided to be deliberately left
;; in.

;; How can we simplify and ensure consistency of the value partition?
;; We can make use of patterns. Just so happens that Tawny supports
;; this particular pattern directly.

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

;; * The full code in these slides can be found in
;; `src/tawny/tutorial/amino_acid_pattern.clj`

;; == And the preamble

;; * This is the same as before

;; [source,lisp]
;; ----
(defontology aa)

(defclass AminoAcid)
;; ----

;; == The `Size` value partition

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

;; * And (some of) the OMN

;; [source,omn]
;; ----
;; ObjectProperty: aa:hasSize
;;     Domain:
;;         aa:AminoAcid

;;     Range:
;;         aa:Size

;;     Characteristics:
;;         Functional

;; Class: aa:Size
;;     EquivalentTo:
;;         aa:Large or aa:Small or aa:Tiny
;; ----

;; == More value partitions

;; * Adding partitions for all the properties is easy

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
;; * Syntactically, defining the ontology has simplified
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

;; * Tawny-OWL directly supports the value partition
;; * This integrates with facets
;; * Together, can simply this (very common) form of ontology
