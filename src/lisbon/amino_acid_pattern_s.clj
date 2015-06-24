;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;;
;; [source,lisp]
;; ----
(ns lisbon.amino-acid-pattern-s
  (:use [tawny owl pattern]))
;; ----


;; ====
;; endif::backend-slidy[]

;; [source,notlisp]
;; ----
;; (ns lisbon.amino-acid-pattern
;;   (:use [tawny.owl]))
;; ----


;; [source,lisp]
;; ----
(defontology aa)

(defclass AminoAcid)


(defpartition Size
  [Tiny Small Large]
  :domain AminoAcid)

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


(as-subclasses
 AminoAcid


 (defclass Alanine
   :super (facet Neutral Hydrophobic NonPolar Aliphatic Tiny))

 (defclass Arginine
   :super (facet Positive Hydrophilic Polar Aliphatic Large))

 (defclass Asparagine
   :super (facet Neutral Hydrophilic Polar Aliphatic Small))

 (defclass Aspartate
   :super (facet Negative Hydrophilic Polar Aliphatic Small))

 ;; and the rest
 )

;; ----
