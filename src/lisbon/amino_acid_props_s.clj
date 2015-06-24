

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; [source,lisp]
;; ----
(ns lisbon.amino-acid-props-s
  (:use [tawny owl pattern]))
;; ----


;; ====
;; endif::backend-slidy[]

;; [source,notlisp]
;; ----
;; (ns lisbon.amino-acid-props
;;   (:use [tawny owl pattern]))
;; ----


;; [source,lisp]
;; ----
(defontology aa)

(defclass AminoAcid)

;; Define a value partition
(defclass Size)
(defoproperty hasSize
  :domain AminoAcid
  :range Size
  :characteristic :functional)

(as-subclasses
 Size
 :disjoint :cover

 (defclass Tiny)
 (defclass Small)
 (defclass Large))


(as-subclasses
 AminoAcid
 :disjoint :cover

 (defclass Alanine
   :super (owl-some hasSize Tiny))

 (defclass Arginine
   :super (owl-some hasSize Large))

 (defclass Asparagine
   :super (owl-some hasSize Small)))


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

(as-facet
 hasCharge

 Positive Neutral Negative)

(refine Alanine
        :super (facet Neutral))

(refine Arginine
         :super (facet Positive))

(refine Asparagine
        :super (facet Neutral))


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
