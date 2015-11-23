;; == Task{counter:task}: Using SIO

;; * See a real use of `tawny.read`
;; * Add links between SIO and our amino-acid ontology


;; == Namespace

;; * As always, we use a new namespace
;; * We `require` the SIO ontology
;; * `sio-maven.sio` is a namespace with a `defread` form in it
;; * We add an alias (to save typing!)

;; [source,notlisp]
;; ----
;; (ns tutorial.amino-acid-sio
;;   (:use [tawny owl pattern])
;;   (:require [sio-maven.sio :as sio]))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; [source,lisp]
;; ----
(ns tawny.tutorial.amino-acid-sio
  (:use [tawny owl pattern])
  (:require [sio-maven.sio :as sio]))
;; ----
;; ====
;; endif::backend-slidy[]

;; == More amino-acids

;; * First we create *another* amino-acid ontology
;; * And import SIO
;; * `sio/sio` means:
;; ** In the namespace `sio`
;; ** Which is an alias to `sio-maven.sio`
;; ** Import the ontology `sio`
;; * And define an amino-acid class

;; [source,lisp]
;; ----
(defontology aasio)

(owl-import sio/sio)

(defclass AminoAcid)
;; ----

;; == Define our value partitions

;; * We define our value partitions as before
;; * But we give them a super class of `chemical_quality`

;; [source,lisp]
;; ----
(defpartition Size
  [Tiny Small Large]
  :domain AminoAcid
  :super sio/chemical_quality)

(defpartition Hydrophobicity
  [Hydrophobic Hydrophilic]
  :domain AminoAcid
  :super sio/chemical_quality)
;; ----

;; == Where does the name come from?

;; * Where does "`chemical_quality`" come from
;; * Actually the label from SIO
;; * The SIO identifier is numeric and impractical

;; [source,lisp]
;; ----
(defpartition SideChainStructure
  [Aromatic Aliphatic]
  :domain AminoAcid
  :super sio/chemical_quality)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We have the same problem as before: SIO identifiers are numeric. Refering to
;; SIO terms by identifier is just a nightmare in this context. We could, of
;; course, use clever tooling to help us with the lookup of IDs, but this would
;; leave the source code unreadable later.

;; So, effectively, we use a human-readable ID, and do the lookup at runtime.
;; There are some disadvantages with this, but tawny offers solutions.
;; SIO labels have spaces in them: so, we use "_". SIO labels name clash with
;; Clojure syntax (so we use sio/_false rather than sio/false).

;; The worst problem is that SIO labels can change. We have a solution (called
;; "memorize") for that also.
;; ====
;; endif::backend-slidy[]

;; == And values

;; * Charge is different
;; * The *values* also have SIO equivalents
;; * We use `refine` to add these.

;; [source,lisp]
;; ----
(defpartition Charge
  [Positive Neutral Negative]
  :domain AminoAcid
  :super sio/chemical_quality)

(refine Positive :equivalent sio/positive_charge)
(refine Negative :equivalent sio/negative_charge)
;; ----

;; == Likewise, Polarity

;; [source,lisp]
;; ----
(defpartition Polarity
  [Polar NonPolar]
  :domain AminoAcid
  :super sio/chemical_quality)

(refine Polar :equivalent sio/polar)
(refine NonPolar :equivalent sio/non-polar)
;; ----

;; == And the output

;; * Back to normal, unreadable, numeric IDs

;; [source,omn]
;; ----
;; Class: <http://semanticscience.org/resource/SIO_001046>
;;     EquivalentTo: aasio:Polar
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Although, for years, I thought numeric identifiers made perfect sense, I start
;; to have my doubts. I think that they have a place, but I think we need a human
;; readable but generally machine safe identifier as well.

;; It's a question open for debate!
;; ====
;; endif::backend-slidy[]

;; == The read process

;; * Is the green arrow in this picture!
;; * The default transformations are shown.

;; image::tawny-name.png[]

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; We have seen this picture before, but without explaining what the green arrows
;; mean. These are the default transformations that we use for the read process,
;; to ensure that the identifiers all work correctly!
;; ====
;; endif::backend-slidy[]

;; == A subtlety

;; * There is something more subtle happening
;; * Where is `sio.owl`?
;; * Actually, it's been packaged as a maven artifact
;; * It's downloaded by leiningen
;; * We have decoupled identifier and transport
;; * And have reused leiningen/maven infrastructure
;; * It is an dependency of this project!

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; The subtle thing going on here is that sio.owl is still available, but it's
;; not obvious where. In our case, we have packaged it (this is our option! We
;; could also load it live, every time). In packaging it, we have borrowed much
;; from maven and leiningen. Someone else can host it for us (in our case, a
;; project called "clojars", but maven central works also). We can borrow maven's
;; standard dependency mechanism -- sio-maven is referenced in the file
;; `project.clj`. And we gain a versioning/release mechanism. SNAPSHOT versions
;; are downloaded regularly. Release versions are stable.
;; ====
;; endif::backend-slidy[]


;; == SIO-maven

;; * Sio-maven was developed by Dr Jennifer Warrender.
;; * Needs a relatively complex `defread` form.
;; * She also wrote Tawny-SIO which is a "port" to Tawny-OWL

;; https://github.com/jaydchan/sio-maven
;; https://github.com/jaydchan/tawny-sio




;; == Task{task}: Conclusions

;; * We can share `read` ontologies written by others
;; * Using SIO is straight-forward and transparent
