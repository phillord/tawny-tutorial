;; == Ontological Hello World

;; * We have a working Clojure "hello world"
;; * Let's build an ontological one


;; == Ontological Hello World

;; * For everything that follows you have:
;; ** An empty file for trying out the examples (`src/lisbon/onto_hello.clj`)
;; ** A full file with all the answers (`src/lisbon/onto_hello_src.clj`)
;; * Better to copy and paste if you can!


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; The slides and tutorial material that I am showing are all developed in files
;; with names `_s.clj`, but I am providing cut-and-paste material for files
;; without the `_s.clj`. This is the real namespace declaration. The false one
;; comes next.

;; [source,lisp]
;; ----
(ns lisbon.onto-hello-s
  (:use [tawny.owl]))
;; ----

;; ====
;; endif::backend-slidy[]

;; == The namespace

;; * Clojure has a namespace mechanism
;; * The namespace is the same as the file name
;; * But `_` in file name is `-` in namespace
;; * `:use` makes `tawny.owl` namespace available

;; [source,notlisp]
;; ----
;; (ns lisbon.onto-hello
;;   (:use [tawny.owl]))
;; ----


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; The Clojure namespace declaration is the only "programmatic" part of tawny
;; that you have to see. Rather like Java, Clojure namespaces are consistent with
;; the filename (although dashes are replaced with underscores for strange
;; reasons). Most development environments will put this in for you.

;; We also add a statement to say that we wish to "use" tawny.owl. This is a
;; file local import, and it will occur a lot!
;; ====
;; endif::backend-slidy[]


;; == Define an Ontology

;; * Using the `defontology` statement
;; * `hello` is a new variable
;; * Can be used to refer to the ontology
;; * Becomes "default" for this namespace
;; * All frames are optional
;; * We see more later

;; [source,lisp]
;; ----
(defontology hello
  :iri "http://www.w3id.org/ontolink/tutorial/hello")
;; ----

;; == Introduce a Class

;; * Use a `defclass` statement
;; * Statements are delineated with `()`
;; * Also called "form" or "sexp" (s-expression)
;; * `Hello` is also a variable
;; * `Hello` and `hello` are different
;; * Cannot use the same name twice
;; * We use the "default" ontology

;; [source,lisp]
;; ----
(defclass Hello)
;; ----

;; == Properties

;; * Properties use `defoproperty`
;; * `o` to distinguish annotation and datatype property
;; * Both `defclass` and `defoproperty` take a number of frames
;; * All are optional

;; [source,lisp]
;; ----
(defoproperty hasObject)
(defclass World)
;; ----

;; == A complex class

;; * Now we use a frame
;; * `:super` says "everything that follows is a super class"
;; * Or, `HelloWorld` has a `super` which is `Hello`
;; * http://www.russet.org.uk/blog/2985
;; * `owl-some` is the existential operator
;; * The default operator (or only) operator is some cases

;; [source,lisp]
;; ----
(defclass HelloWorld
  :super Hello
  (owl-some hasObject World))
;; ----

;; == Save the Ontology

;; * Either add this form to the file and Ctrl-S
;; * Or just type into REPL (window with prompt)
;; * Open `o.omn` either in Catnip, a text editor or Protege to check

;; [source,notlisp]
;; ----
;; (save-ontology "o.omn" :omn)
;; ----

;; == More Frames

;; * Can add new frames to existing definition with `refine`
;; * Could also just change `defclass` form and re-evaluate
;; * `:label` and `:comment` add annotations using OWL built-in
;; * `:annotation` is general purpose frame
;; * We add a label in Portugese
;; * Re-save the ontology

;; [source,lisp]
;; ----
(refine HelloWorld
        :label "Hello World"
        :comment "Hello World is a kind of greeting directed generally at everything."
        :annotation (label "Olá mundo" "pt"))
;; ----
