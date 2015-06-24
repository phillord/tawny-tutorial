;; [source,lisp]
;; ----
(ns lisbon.amino-acid-build-s
  (:use [tawny owl pattern reasoner util]))

(defontology aabuild)

(defclass AminoAcid)

(defclass PhysicoChemicalProperty)

(defpartition Size
  [Tiny Small Large]
  :domain AminoAcid
  :super PhysicoChemicalProperty)

(defpartition Charge
  [Positive Neutral Negative]
  :domain AminoAcid
  :super PhysicoChemicalProperty)

(defpartition Hydrophobicity
  [Hydrophobic Hydrophilic]
  :domain AminoAcid
  :super PhysicoChemicalProperty)

(defpartition Polarity
  [Polar NonPolar]
  :domain AminoAcid
  :super PhysicoChemicalProperty)

(defpartition SideChainStructure
  [Aromatic Aliphatic]
  :domain AminoAcid
  :super PhysicoChemicalProperty)
;; ----


;; [source,lisp]
;; ----
(defdontfn amino-acid [o entity & properties]
  (owl-class o entity
     :super
     (facet properties)))

(defentity defaminoacid
  "Defines a new amino acid."
  'amino-acid)

(defdontfn amino-acids [o & definitions]
  (map
   (fn [[entity & properties]]
     ;; need the "Named" constructor here
     (->Named entity
              (amino-acid o entity properties)))
   definitions))

(defmacro defaminoacids
  [& definitions]
  `(tawny.pattern/intern-owl-entities
    (apply amino-acids
     (tawny.util/name-tree ~definitions))))
;; ----


;; [source,lisp]
;; ----
(as-subclasses
 AminoAcid
 :disjoint :cover

 (defaminoacids
   [Alanine        Neutral  Hydrophobic NonPolar Aliphatic Tiny]
   [Arginine       Positive Hydrophilic Polar    Aliphatic Large]
   [Asparagine     Neutral  Hydrophilic Polar    Aliphatic Small]
   [Aspartate      Negative Hydrophilic Polar    Aliphatic Small]
   [Cysteine       Neutral  Hydrophobic Polar    Aliphatic Small]
   [Glutamate      Negative Hydrophilic Polar    Aliphatic Small]
   [Glutamine      Neutral  Hydrophilic Polar    Aliphatic Large]
   [Glycine        Neutral  Hydrophobic NonPolar Aliphatic Tiny]
   [Histidine      Positive Hydrophilic Polar    Aromatic  Large]
   [Isoleucine     Neutral  Hydrophobic NonPolar Aliphatic Large]
   [Leucine        Neutral  Hydrophobic NonPolar Aliphatic Large]
   [Lysine         Positive Hydrophilic Polar    Aliphatic Large]
   [Methionine     Neutral  Hydrophobic NonPolar Aliphatic Large]
   [Phenylalanine  Neutral  Hydrophobic NonPolar Aromatic  Large]
   [Proline        Neutral  Hydrophobic NonPolar Aliphatic Small]
   [Serine         Neutral  Hydrophilic Polar    Aliphatic Tiny]
   [Threonine      Neutral  Hydrophilic Polar    Aliphatic Tiny]
   [Tryptophan     Neutral  Hydrophobic NonPolar Aromatic  Large]
   [Tyrosine       Neutral  Hydrophobic Polar    Aromatic  Large]
   [Valine         Neutral  Hydrophobic NonPolar Aliphatic Small]))
;; ----


;; [source,lisp]
;; ----
(defclass
  SmallPolarAminoAcid
  :equivalent (owl-and (facet Small Polar)))

(defclass LargeNonPolarAminoAcid
  :equivalent (owl-and (facet (list Large NonPolar))))

(defn amino-acid-def [partition-values]
  (owl-class
   (str
    (clojure.string/join
     (map
      #(.getFragment
        (.getIRI %))
      partition-values))
    "AminoAcid")
   :equivalent (owl-and (facet partition-values))))

(defn cart [colls]
  (if (empty? colls)
    '(())
    (for [x (first colls)
          more (cart (rest colls))]
      (cons x more))))

(doall
 (map
  amino-acid-def
  ;; kill the empty list
  (rest
   (map
    #(filter identity %)
    ;; combination of all of them
    (cart
     ;; list of values for each partitions plus nil
     ;; (so we get shorter versions also!)
     (map
      #(cons nil (seq (direct-subclasses %)))
      ;; all our partitions
      (seq (direct-subclasses PhysicoChemicalProperty))))))))
;; ----



;; [source,lisp]
;; ----
(reasoner-factory :hermit)
(consistent?)
(coherent?)

(count (unsatisfiable))

(count (subclasses AminoAcid))

(count (isubclasses AminoAcid))

(count
 (filter
  #(not (.isDefined % aabuild))
  (isubclasses
   (owl-class "SmallAminoAcid"))))
;; ----
