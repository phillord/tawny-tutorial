(ns lisbon.amino-acid-sio-s
  (:use [tawny.owl])
  (:require [sio-maven.sio :as sio]))

(defontology aasio)

(owl-import sio/sio)

(defclass AminoAcid)

(defpartition Size
  [Tiny Small Large]
  :domain AminoAcid
  :super sio/chemical_quality)

(defpartition Charge
  [Positive Neutral Negative]
  :domain AminoAcid
  :super sio/chemical_quality)

(refine Positive :equivalent sio/positive_charge)
(refine Negative :equivalent sio/negative_charge)

(defpartition Hydrophobicity
  [Hydrophobic Hydrophilic]
  :domain AminoAcid
  :super sio/chemical_quality)

(defpartition Polarity
  [Polar NonPolar]
  :domain AminoAcid
  :super sio/chemical_quality)

(refine Polar :equivalent sio/polar)
(refine NonPolar :equivalent sio/non-polar)

(defpartition SideChainStructure
  [Aromatic Aliphatic]
  :domain AminoAcid
  :super sio/chemical_quality)

;; Class: <http://semanticscience.org/resource/SIO_001046>
;;     EquivalentTo: aasio:Polar
