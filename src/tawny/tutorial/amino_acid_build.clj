;; == Task {counter:task}: Create New Syntax

;; * Create new syntax describing the amino-acids
;; * Create some defined defined classes
;; * Create all the defined classes
;; * Use the reasoner

;; == The Finale

;; * This is rather more advanced
;; * But pulls together most of the strands
;; * Demonstrates the value of a *programmatic* environment
;; * Possible to build ontologies without this.
;; * Sometimes only lambda is enough!
;; * The biology is quite cute also


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; This is going to be a highly advanced, showing an high programmatic use of
;; Tawny. In this course of this we will generate some brand new syntax -- this
;; demonstrates one of the uses of tawny -- while it is harder to generate this
;; new syntax, than just use existing, once you have done it is easier to use.

;; There is also some interesting biology and an interesting ontological question
;; that comes out of the end of it.
;; ====
;; endif::backend-slidy[]


;; == The namespace

;; * Lots of namespaces involved here
;; * Tawny-OWL does have more namespaces
;; * But not many

;; [source,lisp]
;; ----
(ns tawny.tutorial.amino-acid-build
  (:use [tawny owl pattern reasoner util]))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Probably this is an extreme example of `use`. If I were doing this normally, I
;; would almost certainly `require` `pattern`, `reasoner` and `util` through an
;; alias.

;; ====
;; endif::backend-slidy[]


;; == The Upper Ontology

;; * Nothing new here!

;; [source,lisp]
;; ----
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

;; == Defining our AminoAcids

;; * Done this before
;; * It involves too much typing
;; * Want new syntax
;; * Also to help ensure consistency

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Building amino acids by defining a class for each is no good at all, as it's
;; too long. Also, it's too risky. So, we are going to expand the syntax, so that
;; it will work well.
;; ====
;; endif::backend-slidy[]


;; == Defining out AminoAcid

;; * The function is relatively easy
;; * `defdontfn` gives default ontology handling
;; * `& properties` is variadic or "one or more args".
;; * `owl-class` function does NOT define a new variable

;; [source,lisp]
;; ----
(defdontfn amino-acid [o entity & properties]
  (owl-class o entity
     :super
     (facet properties)))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; The function for making a new amino acid is relatively simple, as these
;; things go. It just passes off most of it's work to `owl-class`. We could also
;; add "AminoAcid" as a superclass here, but I chose to do this later for reasons
;; that should become apparent.

;; This function does not intern -- we can define a new amino-acid like this, but
;; the `entity` would be a string and we cannot refer to it afterwards as
;; anything other than a string.

;; We cannot just replace `owl-class` with `defclass` to achieve this. The
;; explanation requires knowledge of lisp, but it is this: because amino-acid is
;; a function it's arguments are evaluated so we cannot pass a bare symbol --
;; Clojure will crash. More `defclass` is a macro, so it will be called when the
;; `amino-acid` is evaluated NOT called. We have to make a macro to do this.
;; ====
;; endif::backend-slidy[]

;; == Making a new variable

;; * If we want to create a new symbol tawny provides `defentity`
;; * It does a few other things as well

;; [source,lisp]
;; ----
(defentity defaminoacid
  "Defines a new amino acid."
  'amino-acid)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; In fact, `defclass`, `defoproperty` and the rest are all defined in this way.
;; This is a common enough thing to want to do, that I made this macro public. It
;; is an easy way, for instance, to add new frames or set default values for
;; existing frames, or to define patterns.

;; `defentity` does one or two other things as well, chiefly adding metadata to
;; the var created. If you don't know what this means (unless you know clojure
;; you probably wont) then it really isn't important.
;; ====
;; endif::backend-slidy[]


;; == Define Lots of Amino Acids

;; * Lets define all the amino-acids at once
;; * Pass definitions as a list (of lists)
;; * Call using `map`
;; * The anonymous function *destructures*
;; * `->Named` packages name and entity together

;; [source,lisp]
;; ----
(defdontfn amino-acids [o & definitions]
  (map
   (fn [[entity & properties]]
     ;; need the "Named" constructor here
     (->Named entity
              (amino-acid o entity properties)))
   definitions))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Better than creating one amino-acid, let's create all of them at once. This
;; function does two things -- it calls the `amino-acid` function over several
;; definitions. And secondly, it bundles the return value with the name of the
;; entity (which is the first element of the definition). We use this next.
;; ====
;; endif::backend-slidy[]

;; == And make variables

;; * We want to use symbols and define a new variable.
;; * Tawny has some support for this
;; * Not going to explain in detail

;; [source,lisp]
;; ----
(defmacro defaminoacids
  [& definitions]
  `(tawny.pattern/intern-owl-entities
    (apply amino-acids
     (tawny.util/name-tree ~definitions))))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We want to transform a bunch of symbols into strings because we are going to
;; use symbols we have not defined yet. We are missing the fact that the symbol
;; name and the string are equivalent here, so we could do this better, but the
;; `name-tree` function is too handy here. `intern-owl-entities` achieves the
;; same thing as defentity.
;; ====
;; endif::backend-slidy[]

;; == Define the amino-acids

;; * Now we can define all the amino-acids in one go.
;; * The syntactic regularity means we are unlikely to miss something.
;; * For me, this makes the effort worth while.
;; * We also define subclasses, disjoints and covering.
;; * Pay attention to the `:cover`

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

;; == Defined Classes

;; - Defined Classes can be reasoned over.
;; - Anything with a `Small` facet is a `SmallAminoAcid`

;; [source,lisp]
;; ----
(defclass SmallAminoAcid
  :equivalent (facet Small))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Defined subclasses are the heart of reasoning in OWL. Effectively, they form
;; queries and they tell us useful things.
;; ====
;; endif::backend-slidy[]

;; == And some more

;; - There are lots of these
;; - We can combine them in many ways.

;; [source,lisp]
;; ----
(defclass
  SmallPolarAminoAcid
  :equivalent (owl-and (facet Small Polar)))

(defclass LargeNonPolarAminoAcid
  :equivalent (owl-and (facet Large NonPolar)))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; So, having done one, surely we should do more. So here are the next two.
;; Here we are combining two.
;; ====
;; endif::backend-slidy[]

;; == Where to stop

;; * Where to stop
;; * 3? 10?
;; * Why not do them all?
;; * We are using a programmatic tool
;; * How would we do this


;; == A defined class function

;; * Similar to before
;; * This does not create variables

;; [source,lisp]
;; ----
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
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We can build a function to replicate this. We use some string manipulation for
;; this to generate the name. I have not done the interning here -- I leave this
;; an exercise!
;; ====
;; endif::backend-slidy[]

;; == Doing them all

;; * "Doing them all" actually means the cartesian product
;; * Surprisingly there is not a function for this
;; * This is pure Clojure, not doing to describe it

;; [source,lisp]
;; ----
(defn cart [colls]
  (if (empty? colls)
    '(())
    (for [x (first colls)
          more (cart (rest colls))]
      (cons x more))))
;; ----

;; == Doing them all

;; * Call the `amino-acid-def` function on cartesian product
;; * This creates 453 defined classes

;; [source,lisp]
;; ----
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


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We now call actually run the cartesian product. We add "nil" so that we get
;; single, double, and triple as well as full length products, and filter for nil
;; to get rid of them again.
;; ====
;; endif::backend-slidy[]

;; == Reasoning

;; * Finally, we reason over this.
;; * We choose to use hermit
;; * And check consistency
;; * This takes a second or two

;; [source,lisp]
;; ----
(reasoner-factory :hermit)
(consistent?)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Tawny support a couple of reasoners out of the box, including Hermit and ELK.
;; Here we are instantiating using a `:keyword`, but this is just a short-cut --
;; any OWL API `OWLReasonerFactory` can be used directly.

;; The reasoner is invoked to check consistency automatically. Tawny uses a GUI
;; (a progress bar) by default to show this process, but falls back to text if
;; that is not possible (so you can check consistency in a CI environment without
;; hassles.
;; ====
;; endif::backend-slidy[]

;; == Reasoning

;; * We can count numbers
;; * We have reasoned many subclases of `AminoAcid`

;; [source,lisp]
;; ----
(count (subclasses AminoAcid))
(count (isubclasses AminoAcid))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Working out what has happened can be quite hard (this is something that we
;; wish to fix in future versions of tawny), but counting subclasses work as well
;; as anything. We now have a lot more inferred subclasses than asserted.
;; ====
;; endif::backend-slidy[]

;; == Reasoning

;; * But we are not coherent!
;; * In fact, we have many unsatisfiable classes
;; * What is happening?

;; [source,lisp]
;; ----
(coherent?)

(count (unsatisfiable))
;; ----

;; == Visualising

;; * Are many ways to visualize our ontology
;; * Saving it and opening in Protege is easiest


;; [source,notlisp]
;; ----
;; (save-ontology "o.owl" :owl)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Protege is really nice for visualising ontologies. So, we use that here.
;; I always save to the same file name, but better to save as OWL rather than OMN
;; because it parses more correctly.
;; ====
;; endif::backend-slidy[]

;; == Visualising

;; * Many defined classes are equiavlent
;; * Many are unsatifisable
;; * Happens because there are 20 amino-acids
;; * But 700 defined classes
;; * Many defined classes have necessarily the same extent
;; * Many can have no individuals (`Negative` and `Hydrophobic`)
;; * Only happens *with* the covering axiom


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; The reason that this happens is obscure, perhaps, but the base reason is
;; because we have many more defined classes than we have primitive ones. So, we
;; must have equivalences or unsatisifiables.

;; This only happens because of the magic `:cover` axiom. We know all of the
;; amino acids that exist --- as there are no hydrophobic charged amino-acids (as
;; the two facets are not independent for obvious reasons of chemistry!), that
;; class becomes inconsistent. Without the `:cover` axiom, the reasoner would
;; assume that this class *could* exist but we have just not mentioned it.

;; So, the reasoning is telling us something about the biology -- and whether we
;; want this form of conclusion depends on whether we are talking about biology
;; or chemistry -- after all if we were a chemist many amino acids could be
;; created that separate out of equivalent classes, and many make some
;; unsatisifiable classes satisfiable.

;; Life is complex but, in this case, simpler than chemistry.
;; ====
;; endif::backend-slidy[]


;; == As a query

;; * Using defined classes as a query
;; * Not that useful
;; * Most of the infered subclasses are defined!

;; [source,lisp]
;; ----
(count
 (isubclasses SmallAminoAcid))
;; ----

;; == As a query

;; * We have a full programming language
;; * So, we filter for only undefined classes

;; [source,lisp]
;; ----
(count
 (filter
  #(not (.isDefined % aabuild))
  (isubclasses SmallAminoAcid)))
;; ----


;; ==  Task {task}: Conclusions

;; * We can use highly programmatic nature of Tawny
;; * We can generate many defined classes
;; * To do so is useful
;; * In this case one axiom can have a large effect
;; * Results depend on the choices we make in the modelling
