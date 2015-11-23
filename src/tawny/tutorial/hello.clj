;; == Paying the Piper

;; * Tawny-OWL has many benefits, out-of-the-box
;; * Comes with one significant cost
;; * Requires a working Clojure environment
;; * And an understanding of how to use it


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; While Tawny-OWL does come with many significant benefits as an ontology
;; development, it also comes with one significant cost. It requires the use of a
;; Clojure development environment. In this short section, we will get "hello
;; world" up and running.
;; ====
;; endif::backend-slidy[]

;; == Clojure environments

;; * IDEs provide a shrink-wrapped environment
;; * But come with their own baggage


;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; I could have opted to use a shrink-wrapped IDE. But while these provide a very
;; rich environment, they also come with an enormous amount of baggage, and give
;; a very programmatic feel to the experience. They also tend to use a lot of
;; resource, which might not be ideal on laptops.

;; More over, the practical reality is that I do not use one myself and can only
;; give partial help on them.
;; ====
;; endif::backend-slidy[]

;; == Catnip

;; * I've opted for "catnip"
;; * A light and simple Clojure editor
;; * Useful for tutorials!

;; ifndef::backend-slidy[]
;; [NOTE]
;; ====
;; Instead I have opted for catnip. This is a small, light and simple clojure
;; editor, which was primarily designed as a tutorial environment for Clojure. It
;; is, itself, a Clojure application and works quite nicely.

;; It is not, of course, a fully featured environment. If you want to develop
;; seriously, them you'd need to move to something else.

;; It does, however, reinforce the point that we are using a commodity language.
;; There are many powerful tools around.
;; ====
;; endif::backend-slidy[]

;; == Catnip

;; * Assuming you have followed pre-requisities launch with:

;; `lein edit`

;; * It should print

;; `Catnip running on http://localhost:nnnn`

;; * And with luck a web browser will pop up.

;; == Catnip

;; * Open the file `src/tutorial/hello.clj`
;; * Save the file with Ctrl-S
;; * This *also* compiles the file
;; * Ctrl-R or click where it says `tutorial.hello`
;; * Type `hello`

;; [source,lisp]
;; ----
(ns tawny.tutorial.hello)

(def hello "hello world")
;; ----

;; == What have we achieved

;; * We have defined a new *namespace*
;; * Created a variable (`hello`)
;; * *Evaluated* `hello` to find it's value (prosaically "hello world")
;; * This is the basic Clojure workflow
