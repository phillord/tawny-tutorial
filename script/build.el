;;
;; Generate lentic documentation
;;
(require 'lentic)
(require 'lentic-asciidoc)
(require 'commander)
(require 'load-relative)



(defun gensource-and-report (file init)
  (message "Cloning %s..."
           file)
  (let ((config
         (car
          (lentic-batch-clone-and-save-with-config
           file init))))
    (message "Cloning %s...done" file)
    (message "For %s generated %s."
             file
             (oref config :lentic-file))))

(require 'lentic-doc)
(defun gensource-gen-if-necessary (file)
  (let* ((target
          ;; switch for f-swap-ext in due course -- remove lentic-doc require!
          (lentic-f-swap-ext file "adoc"))
         (locked
          (or (file-locked-p file)
              (file-locked-p target))))
    (if locked
        (message "Skiping %s due to lock %s" file locked)
      (if (file-newer-than-file-p file target)
          (gensource-and-report file 'lentic-clojure-asciidoc-init)
        (message "File uptodate: %s" file)))))


(defun gensource-clean-if-possible (file)
  (let* ((target
          ;; switch for f-swap-ext in due course -- remove lentic-doc require!
          (lentic-f-swap-ext file "adoc"))
         (locked
          (or (file-locked-p file)
              (file-locked-p target))))
    (if locked
        (message "Skipping %s due to lock %s" file locked)
      (message "Cleaning %s..." target)
      (f-delete target)
      (message "Cleaning %s...done" target))))

(defun build/gen-src ()
  (mapc
   (lambda (file)
     (gensource-gen-if-necessary
      (concat "./src/lisbon/" file)))
   build-sources))

(defun build/clean-src ()
  (mapc
   (lambda (file)
     (gensource-clean-if-possible
      (concat "./src/lisbon/" file)))
   build-sources))

(defvar build-sources
  '("core.clj"))

(commander
 (command "gen-src" "Generate adoc from Clojure" build/gen-src)
 (command "clean-src" "Clean adoc from Clojure" build/clean-src))
