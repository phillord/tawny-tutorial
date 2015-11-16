;; == Task {counter:task}

;; Build a Hello World Ontology!

;; == Ontological Hello World

;; * We have a working Clojure "hello world"
;; * Let's build an ontological one


;; == Ontological Hello World

;; * For everything that follows you have:
;; ** An empty file for trying out the examples (`src/tutorial/onto_hello.clj`)
;; ** A full file with all the answers (`src/tutorial/onto_hello_s.clj`)
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
(ns tutorial.onto-hello-s
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
;; (ns tutorial.onto-hello
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

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Next we define a new ontology. It has a name that we can use to refer to it,
;; which is a useful property as we shall see, although most of the time, we do
;; not have to. For the `defontology` statement everything except for the name is
;; optional, although there are quite a few frames more of which we will use as
;; we move through the tutorial.

;; The name is also used as a prefix when saving the ontology (although this can
;; be overriden), so I tend not to use hyphens, as it messes with syntax
;; hightlight for my Manchester syntax viewer.
;; ====
;; endif::backend-slidy[]


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

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====

;; Now we define a new class. As we can see, by comparing against the
;; `defontology`, statements, we have parenthical statements all the way through.
;; This form of statement is also known in lisp speak as a "form", an
;; "s-expression" (to distinguish is from m-expressions which don't exist!) or
;; rather more obscurely "sexp".

;; One key point from a programming point-of-view, clojure is a lisp-1. All
;; variables are in the same namespace, and that includes all the ontology
;; entities that we might define. It's easy to clobber one with another so be
;; careful!

;; ====
;; endif::backend-slidy[]


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

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Properties use defoproperty. There are also annotation and object properties,
;; and I have opted for somewhat opaque function names to avoid a large amount of
;; typing. Hard to know whether this was a great decision or not, but the
;; alternative really seemed various unreadable to me. More over, there are only
;; a few of these names, and OWL 3 is unlikely to come along any time soon.
;; ====
;; endif::backend-slidy[]


;; == A complex class

;; * Now we use a frame
;; * `:super` says "everything that follows is a super class"
;; * Or, `HelloWorld` has a `super` which is `Hello`
;; * http://www.russet.org.uk/blog/2985
;; * `owl-some` is the existential operator
;; * The default operator (or only) operator in many ontologies

;; [source,lisp]
;; ----
(defclass HelloWorld
  :super Hello
  (owl-some hasObject World))
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Finally, we bring in a frame. Frames are introduced using "keywords" -- that
;; is something beginning with a ":" -- this is a fundamental part of lisp syntax
;; (it is one of two ways of defining self-evaluating forms if you are
;; interested!), which by good fortune is also very similar to the way that
;; Manchester syntax does it.

;; Hopefully, most people are familiar with the meaning of "some" in this context.
;; ====
;; endif::backend-slidy[]


;; == On the use of "owl" (a quick diversion)

;; * It is `owl-some` rather than `some`.
;; * But it is `only` and not `owl-only`
;; * This avoids a name clash with `clojure.core`
;; * Have not gone the OWL API route

;; [source,notlisp]
;; ----
;; (owl-some hasObject World)
;; (only hasObject World)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; As we move through the material, you will notice the "owl" prefix popping up.
;; Although, clojure includes full namespace support clashes with the
;; `clojure.core` are still somewhat of a pain. Therefore, I have avoided them in
;; tawny.owl. In this, I have followed the OWL API. However, I have not done so
;; consistently; the OWL API does by putting "OWL" at the beginning of
;; everything.
;; ====
;; endif::backend-slidy[]


;; == On the use of "owl"

;; * Full list of "owl" prefixed functions is

;; ** owl-and
;; ** owl-or
;; ** owl-not
;; ** owl-some
;; ** owl-class
;; ** owl-import
;; ** owl-comment

;; * And the variables

;; ** owl-nothing
;; ** owl-thing


;; == On the use of "owl"

;; * There are also some "short-cuts"

;; ** `&&`, `||` and `!`

;; * A "long-cuts" (`owl-only`)
;; * And consistent but clashing names in `tawny.english`.

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; There are also options. We have symbol short-cuts which should be obvious to
;; programmers. We have an `owl-only` function which I used to use because I used
;; to forget which is the `clojure.core` function and which was not. And I have
;; consistent namespace called `tawny.english`, but this requires using the
;; namespace mechanism of Clojure in a way that is slightly more complex than I
;; wish to describe.
;; ====
;; endif::backend-slidy[]


;; == Save the Ontology

;; * Either add this form to the file and Ctrl-S
;; * Or just type into REPL (window with prompt)
;; * Open `o.omn` either in Catnip, a text editor or Protege to check

;; [source,notlisp]
;; ----
;; (save-ontology "o.omn" :omn)
;; ----

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; To check my axiomatisation I often save my ontology in manchester syntax -- I
;; normally always use the same filename (makes using git, and .gitignore
;; easier). And I have my editor auto-reload. We will be looking at how to build
;; an auto-save function later.
;; ====
;; endif::backend-slidy[]


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

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Finally, let's extend our definition somewhat. We could, of course, just
;; change the defclass statement, but I wanted to introduce the `refine` function
;; which allows addition of new frames to an existing definition. This is quite
;; useful. You can different frames for different types of entity, but only the
;; correct frames for each type of entity (although many frames overlap).

;; Tawny has a number of convenience frames that have no direct equivalent
;; in OMN. `:label` adds labels in English, and `:comment` does the equivalent.
;; Obviously you can specify any type of annotation you wish, and tawny can build
;; internationalized ontologies perfectly well.
;; ====
;; endif::backend-slidy[]


;; == Task {task}: Conclusions

;; * Tawny-OWL uses *frames*
;; * We look like Manchester syntax
;; * But in a lispy way
;; * Easy to type, including short-cuts

