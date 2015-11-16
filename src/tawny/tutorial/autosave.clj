;; == Task {counter:task}: Programming an Autosave

;; * Extend Tawny, so that it saves the ontology on every change

;; == Extending Tawny

;; * Tawny is implemented directly in Clojure
;; * We can extend in the same syntax
;; * Can extend in general and ontology specific ways

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; I've picked an autosave because it is a nice general function that we might
;; want to use and demonstrates some of the possibilities. But ontology specific
;; is perhaps most useful of all, because it allows ontology development groups
;; to tailor Tawny to their own development practices without having to generate
;; bespoke extensions for Protege or equivalent.
;; ====
;; endif::backend-slidy[]


;; == Namespace

;; * The namespace definition here is a bit different
;; * Require `tawny.owl` through an alias
;; * Also import an OWL API interfaces


;; [source,notlisp]
;; ----
;; (ns tutorial.autosave
;;   (:require [tawny.owl :as o])
;;   (:import [org.semanticweb.owlapi.model.OWLOntologyChangeListener]))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; In general the use of `:use` clauses is falling out of favour in clojure
;; circles; it may disappear in future versions (although it will be replaced
;; with something else somewhat more verbose). The reason for this is that `:use`
;; is a hostage to the future. If I `use` a namespace and it gains a new
;; function, we could end up with a name collison. So, if `clojure.core` adds an
;; `only` function, it would require changes to any namespace which `use`'d
;; `tawny.owl`

;; For my own use, I think that with tawny this risk is worth it (and it is easy
;; to fix if it happens). For ontology namespaces (i.e. those in which I
;; define an ontology not much else), I tend `use tawny.owl`. For namespaces in
;; which I want to extend `tawny.owl`, I tend to `require tawny.owl` normally
;; aliased to `o`.

;; The name collison between `import` and `owl-import` is sort of an example of
;; this problem.

;; [source,lisp]
;; ----
(ns tutorial.autosave-s
  (:require [tawny.owl :as o])
  (:import [org.semanticweb.owlapi.model.OWLOntologyChangeListener]))
;; ----
;; ====
;; endif::backend-slidy[]


;; == Saving the Listener

;; * We need a variable in which to save our listener
;; * Clojure variables are immutable
;; * Stores a `atom` and change that

;; [source,lisp]
;; ----
(def auto-save-listener
  "The current listener for handling auto-saves or nil." (atom nil))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Clojure was designed for concurrency and generally does not allow changing
;; variables (although it does allow re-evaluation). Instead it has a set of
;; objects which can store any value and which can be changed but which require
;; the developer to make an explicit choice about how to deal with concurrent
;; changes.

;; It's very nice and very sensible, but largely we just ignore it here. The
;; reality with tawny is that the it's build on the OWL API which is mutable, is
;; not thread-safe and is not build for concurrency.
;; ====
;; endif::backend-slidy[]

;; == The auto-save function

;; * OWL API has an listener for ontology changes
;; * We address it directly here.

;; [source,lisp]
;; ----
(defn auto-save
  "Autosave the current ontology everytime any change happens."
  ([o filename format]
   (let [listener (proxy [org.semanticweb.owlapi.model.OWLOntologyChangeListener]
               []
             (ontologiesChanged[l]
               (o/save-ontology o filename format)))]
     (reset! auto-save-listener listener)
     (.addOntologyChangeListener
      (o/owl-ontology-manager) listener)
     listener)))
;; ----

;; == auto-save in detail

;; * Instantiate an object which implements OWLOntologyChangeListener
;; * Implement a single method of this
;; * Just save the ontology
;; * proxy is a *closure*
;; * `o`, `filename`, `format` are closed over

;; [source,notlisp]
;; ----
;; (proxy [org.semanticweb.owlapi.model.OWLOntologyChangeListener]
;;        []
;;    (ontologiesChanged[l]
;;       (o/save-ontology o filename format)))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; This is our actual listener. Proxy objects are available in the Java core, and
;; the Clojure ones do much the same thing -- implement an interface on the fly.
;; They are rather more common in Clojure because a) it makes more sense in
;; Clojure and b) implementing a new class is a bit of a pain (although entirely
;; possible).

;; Proxy objects work through reflection and have the performance characteristics
;; that you would expect -- but this is meant to be an end user function, so we
;; really don't care. Saving the ontology is likely to take far more effort than
;; a reflective call.
;; ====
;; endif::backend-slidy[]

;; == auto-save in detail

;; * save the listener
;; * discarding any existing one!

;; [source,notlisp]
;; ----
;;    (reset! auto-save-listener listener)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Clojure follows the scheme convention of marking mutating functions with a
;; `!`. Tawny doesn't because we'd add `!` to everything. Generally speaking
;; `reset!` is considered rather bad form -- Clojure programmers only change an
;; atom by applying a function to the existing value.

;; Dropping the existing value is, of course, wrong and a memory leak, since the
;; existing listener will be held by the manager, and may even result in strange
;; behaviour.

;; It's a demo!
;; ====
;; endif::backend-slidy[]

;; == auto-save in detail

;; * The `o/owl-ontology-manager` function returns an `OWLOntologyManager`
;; * Clojure uses the `.` syntax to call methods
;; * So, call `addOntologyChangeListener` on the manager
;; * With `listener` as an argument

;; [source,notlisp]
;; ----
;; (.addOntologyChangeListener
;;      (o/owl-ontology-manager) listener)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; I've used Clojure's java interaction extensively during the development of the
;; Tawny and I have to say I found it to be very nice. It fits very comfortably
;; with normal Clojure development.

;; In general, I hide the existance of the `OWLOntologyManager`, and the
;; `OWLOntologyDataFactory`. Generally, there is only one. There are more
;; flexible approaches, of course, but I have had to make decisions in the
;; development of tawny. Otherwise, pretty much every function would require a
;; Manager, or Factory and an ontology as a argument and tawny, as it stands
;; would have become unusable.

;; You might be wondering why `owl-ontology-manager` is a function call and not
;; just a variable. Actually, it is because of the latter -- I have so far found
;; one occasion where I want multiple managers which is for protege integration.
;; Turning this into a function allowed me to monkey-patch tawny for this
;; purpose.

;; It would be possible to deal with this much more formally, and pass an
;; environment, probably integrated with the "default-ontology" functionality of
;; Tawny. But I have not found a strong use case for this yet.
;; ====
;; endif::backend-slidy[]

;; == Remove the auto-save

;; * And a function to reverse the process
;; * `@` dereferences the `atom`

;; [source,lisp]
;; ----
(defn auto-save-off
  "Stop autosaving ontologies."
  []
  (when @auto-save-listener
    (.removeOntologyChangeListener
     (o/owl-ontology-manager)
     @auto-save-listener)))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; And finish off. If you don't know what `@` and dereferencing mean, it's really
;; not interesting.
;; ====
;; endif::backend-slidy[]


;; == auto-save

;; * There is already an `auto-save` function in `tawny.repl`
;; * And an `on-change` function

;; == Task {task}: Conclusion

;; * Clojure provides easy interop with Java
;; * We can use this to extend Tawny-OWL capabilities

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; So, this has been a very rapid run through of how to integrate directly with
;; the OWL API and add new functionality to Tawny that cannot be achieved through
;; tawny itself.

;; This is very commonly done in Clojure (where the mantra is do not wrap if you
;; do not need to) and so it is well supported.
;; ====
;; endif::backend-slidy[]
