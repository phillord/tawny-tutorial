;; == Task {counter:task}: Defining some properties

;; * A list of amino acids only gets us so far
;; * Now we define some properties
;; * First, we do this the long-hand way

;; == Namespace

;; * By now you should be familiar with the namespace definition
;; * It is different
;; * This `:use` clause means "use both `tawny.owl` and `tawny.pattern`"

;; [source,lisp]
;; ----
(ns tawny.tutorial.amino-acid-props
  (:use [tawny owl pattern]))
;; ----

;; * The full code in these slides can be found in
;; `src/tawny/tutorial/amino_acid_props.clj`

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; As usual we start with the namespace declaration; you should be
;; familiar with now. However this is slightly different. In addition
;; to the tawny-owl, We want to "use" tawny-pattern namespace.

;; ====
;; endif::backend-slidy[]

;; == Starting our ontology

;; * As before, we define (another) amino acid ontology

;; [source,lisp]
;; ----
(defontology aa)

(defclass AminoAcid)
;; ----

;; == Amino Acid properties

;; * Amino Acids have many properties
;; * Most of these are _continuous_ values
;; * Hard to model ontologically

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Now let's discuss the properties themselves.

;; Amino acids have many properties e.g. size and polarity.

;; Most of these properties are continuous e.g. hydrophobicity values
;; range from -7.5 and 3.1.

;; The ontological modelling of continuous values is hard.

;; It is, of course, possible to model continuous values as datatype
;; properties and just put the numbers in. This works to a certain
;; extent and is an option for modelling.

;; ====
;; endif::backend-slidy[]

;; == The Value Partition

;; * We introduce the "value partition" pattern
;; * We split a continuous range up into discrete chunks
;; * Like the colours of the rainbow
;; * First we define the partition itself
;; * `AminoAcid`'s have a size, and only one size!

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

;; However the most common approach ontologists use instead is the
;; value partition.

;; The value partition process involves the splitting up a continuous range
;; into discrete chunks. For example splitting the spectrum of colours
;; found on rainbow into seven "bins"/"values".

;; For full details and a discussion of the advantages and disadvantages of this
;; approach, the value partition is described in this recommendation edited by
;; Alan Rector.

;; http://www.w3.org/TR/swbp-specified-values/

;; First we define the partition for size by:
;; 1. creating a Size class
;; 2. defining a hasSize object property
;; a. that ensures that AminoAcid has a size, and only one size

;; ====
;; endif::backend-slidy[]

;; == The Value Partition

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

;; Next we define the values of Size
;; There are only three values i.e. Tiny, Small, Large
;; All of the values are different

;; So we use the as-subclasses function, :disjoint and :cover keywords
;; discussed in the task 2.

;; In a "real" ontology it would be good to add annotation properties and
;; comments describing exactly what these partitions mean.

;; ====
;; endif::backend-slidy[]


;; == Using the values

;; * We can now create our amino acids using these three sizes
;; * We only create three amino acids here
;; * More would be needed

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

 ;;and the rest
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Once defined, we can use these values in our class definitions.

;; e.g. Alanine hasSize some Tiny

;; ====
;; endif::backend-slidy[]

;; == Interim Summary

;; * Defined the Size value partition
;; * Used these values in our class definitions
;; * Issues with these restriction declarations?
;; ** Long-winded
;; ** Duplication
;; ** Error-prone
;; * What can we do to overcome these?

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; What have we done?
;; - defined the Size value partition
;; - used these values in our class definitions

;; This way of declaring restrictions is OK but:

;; - its long-winded and involves a lot of typing

;; - contains a lot of duplication: 20 amino acids means 20
;; existential restrictions of similar structure
;; - what if we add another property?
;; - for each new property that's another 20 existential restrictions
;; of similar structure

;; - with a bigger ontology with many classes and properties it is
;; possible to accidently create an existential with the correct
;; property but incorrect value or vice versa

;; What can we do to overcome these issues?

;; ====
;; endif::backend-slidy[]

;; == Using Facets

;; * Relatively new feature of Tawny-OWL
;; * Call these "facets" after "faceted classification"
;; * Useful when many values are associated with a property
;; * For example value partition
;; * First define `Charge` using the same pattern as `Size`

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

;; We can use facets; a relatively new feature of Tawny.

;; Facets is named after faceted classification; a classification
;; scheme for organising knowledge in a systematic order. You will
;; have probably seen it on Amazon, Ebay, etc. for filtering search
;; results e.g. location, buy it now.

;; See https://en.wikipedia.org/wiki/Faceted_classification for a
;; description of a facetted classification.

;; Extremely useful when many values are associated with a property
;; e.g. value partition

;; But first, let's define the Charge value partition using the same
;; pattern as Size.

;; ====
;; endif::backend-slidy[]


;; == Facet

;; * Now define the facet of `hasCharge`

;; [source,lisp]
;; ----
(as-facet
 hasCharge

 Positive Neutral Negative)
;; ----

;; * facets are _extra-logical_
;; * They do *not* change the semantics of ontology statements
;; * They *are* visible in the ontology

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Now define the facet; we ensure that the values Positive, Neutral
;; and Negative are associated with the hasCharge property.

;; These facets are extra-logical; they do not change the semantics
;; of ontology statements but are visible in the ontology as
;; annotation axioms

;; I had a number of options for the implementation of facets. It would be
;; possible to do this without having them visible in the ontology, but this
;; seemed to the best way forward.

;; ====
;; endif::backend-slidy[]

;; == Using the facet

;; * Can now give just the class
;; * Again, using `refine` although could just alter the code
;; * `(facet Neutral)` rather than `(owl-some hasCharge Neutral)`
;; * Saves us some typing

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

;; Why is this useful?

;; Now we can just provide the value and Tawny will convert the facet
;; into an existential restriction.

;; Saves on some typing

;; ====
;; endif::backend-slidy[]

;; == And others

;; * But we added the `as-facet` declaration
;; * Now let's add `Hydrophobicity`

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

;; Not entirely true; We have to add the `as-facet` call so, overall,
;; the reduction in typing is not enormous, although it's a fixed
;; cost, while the saving is linear with the number of amino acids, so
;; the real saving would be a bit greater.

;; ====
;; endif::backend-slidy[]


;; == Using Facets

;; * And `Polarity`

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
;; * We could do all four value partitions at once

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

;; The advantage goes beyond typing, of course, this also is an
;; advantage in the consistency of building our ontology. We cannot
;; associate the wrong class with the wrong property. In this case, we
;; have set the logical axioms up such that if we did use the wrong
;; property, the reasoner is likely to pick the complaint
;; up anyway. But this is immediate and at the point of use.
;; ====
;; endif::backend-slidy[]


;; == Using Facets

;; * And the output

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

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Here is the complete class definition of Alanine in OMN format

;; ====
;; endif::backend-slidy[]

;; == Task {task}: Conclusions

;; * Can use value partitions to split up numerical ranges
;; * Can define facets to ease the use of object properties
;; * Can apply several facets at once
