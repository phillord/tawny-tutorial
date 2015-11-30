;; == Task {counter:task} -- Defining some properties

;; * A List of amino acids only gets us so far
;; * Now we define some properties
;; * First, we do this the long-hand way.

;; == Namespace

;; * By now you shold be familiar with the name space definition
;; * It is different.
;; * The `:use` clause means "use both `tawny.owl` and `tawny.pattern`".

;; [source,lisp]
;; ----
(ns tawny.tutorial.amino-acid-props
  (:use [tawny owl pattern]))
;; ----

;; == Starting our ontology

;; * As before, we define (yet another!) amino acid ontology

;; [source,lisp]
;; ----
(defontology aa)

(defclass AminoAcid)
;; ----

;; == Amino Acid properties

;; * Amino Acids have many properties
;; * Most of these are _continuous_
;; * Hard to model Ontologically

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; It is, of course, possible to model continuous values as data type properties
;; and just put the numbers in. This works to a certain extent and is an option
;; for modelling.

;; ====
;; endif::backend-slidy[]

;; == The Value Paritition

;; * We introduce the "value partition"
;; * We split a continous range up into discrete chunks
;; * Like the colours of the rainbow
;; * First we define the partition itself
;; * AminoAcid's have a size, and only one size!


;; [source,lisp]
;; ----
(defclass Size)
(defoproperty hasSize
  :domain AminoAcid
  :range Size
  :characteristic :functional)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; For full details and a discussion of the advantages and disadvantages of this
;; approach, the value partition is described in this recommendation edited by
;; Alan Rector.

;; http://www.w3.org/TR/swbp-specified-values/

;; Putting "Size" under a ValuePartition superclass would also make sense.

;; ====
;; endif::backend-slidy[]

;; == Value Partition

;; * Now we define the values
;; * Three values, and only three values
;; * All of which are different

;; [source,lisp]
;; ----
(as-subclasses
 Size
 :disjoint :cover

 (defclass Tiny)
 (defclass Small)
 (defclass Large))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; In a "real" ontology it would be good to add annotation properties and
;; comments describing exactly what these partitions mean.
;; ====
;; endif::backend-slidy[]


;; == Using the values

;; * We can now create our amino-acids using these three sizes.
;; * We only create three amino-acids here
;; * More would be needed.

;; [source,lisp]
;; ----

(as-subclasses
 AminoAcid
 :disjoint :cover

 (defclass Alanine
   :super (owl-some hasSize Tiny))

 (defclass Arginine
   :super (owl-some hasSize Large))

 (defclass Asparagine
   :super (owl-some hasSize Small)))
;; ----


;; == Using Facets

;; * Many classes are associated with properties
;; * Call these "facets" after "facetted classification".
;; * Value Partition is a good example
;; * Define `Charge` using the same pattern as `Size`.

;; [source,lisp]
;; ----
(defclass Charge)
(defoproperty hasCharge
  :domain AminoAcid
  :range Charge
  :characteristic :functional)

(as-subclasses
 Charge
 :disjoint :cover

 (defclass Positive)
 (defclass Neutral)
 (defclass Negative))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; See https://en.wikipedia.org/wiki/Faceted_classification for a description of
;; a facetted classification.

;; Facets are a relatively new feature of tawny-owl and I may come to regret the
;; name as "facet" also has a meaning wrt to the OWL specification (it's a
;; feature of a datatype property). The tawny-owl facet is unrelated to the OWL
;; specification.
;; ====
;; endif::backend-slidy[]


;; == Facet

;; * Now define the values as facet of `hasCharge`
;; * facets are _extra-logical_
;; * They do *not* change the semantics of ontology statements
;; * They *are* visible in the ontology

;; [source,lisp]
;; ----
(as-facet
 hasCharge

 Positive Neutral Negative)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; I had a number of options for the implementation of facets. It would be
;; possible to do this without having them visible in the ontology, but this
;; seemed to the best way forward.
;; ====
;; endif::backend-slidy[]

;; == Using the facet

;; * Can now give just the class
;; * Again, using `refine` although could just alter the code
;; * `(facet Neutral)` rather than `(owl-some hasCharge Neutral)`
;; * Saves some typing

;; [source,lisp]
;; ----

(refine Alanine
        :super (facet Neutral))

(refine Arginine
         :super (facet Positive))

(refine Asparagine
        :super (facet Neutral))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We convert the facet into an existential restriction.
;; ====
;; endif::backend-slidy[]

;; == And others

;; * Does not save that much typing
;; * Let's add `Hydrophobicity`

;; [source,lisp]
;; ----
(defclass Hydrophobicity)
(defoproperty hasHydrophobicity
  :domain AminoAcid
  :range Hydrophobicity
  :characteristic :functional)

(as-subclasses
 Hydrophobicity
 :disjoint :cover

 (defclass Hydrophobic)
 (defclass Hydrophilic))

(as-facet
 hasHydrophobicity

 Hydrophilic Hydrophobic)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We have to add the `as-facet` call so, overall, the reduction in typing is not
;; enormous, although it's a fixed cost, while the saving is linear with the
;; number of amino-acids, so the real saving would be a bit greater.
;; ====
;; endif::backend-slidy[]


;; == Using Facets

;; * And Polarity.

;; [source,lisp]
;; ----
(defclass Polarity)
(defoproperty hasPolarity)

(as-subclasses
 Polarity
 :disjoint :cover

 (defclass Polar)
 (defclass NonPolar))

(as-facet
 hasPolarity

 Polar NonPolar)
;; ----

;; == Using Facets

;; * `facet` broadcasts
;; * We can apply two at once
;; * A different property can be used for each
;; * We could do all four value partitions at once!

;; [source,lisp]
;; ----
(refine
 Alanine
 :super (facet Hydrophobic NonPolar))

(refine
 Arginine
 :super (facet Hydrophilic Polar))

(refine
 Asparagine
 :super (facet Hydrophilic Polar))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Now we can see the full saving advantage. Instead of four separate
;; statements, we have a single one.

;; The advantage goes beyond typing, of course, this also is an advantage in the
;; consistency of building our ontology. We cannot associate the wrong class with
;; the wrong property. In this case, we have set the logical axioms up such that
;; if we did use the wrong property, the reasoner is likely to pick the complaint
;; up anyway. But this is immediate and at the point of use.
;; ====
;; endif::backend-slidy[]


;; == Using Facets


;; * And the output.

;; [source,omn]
;; ----
;; Class: aa:Alanine

;;     SubClassOf: 
;;         aa:hasCharge some aa:Neutral,
;;         aa:AminoAcid,
;;         aa:hasSize some aa:Tiny,
;;         aa:hasHydrophobicity some aa:Hydrophobic,
;;         aa:hasPolarity some aa:NonPolar

;; ----


;; == Task {task}: Conclusions

;; * Can use value partitions to split up numerical ranges
;; * Can define facets to ease the use of object properties
;; * Can apply several facets at once
