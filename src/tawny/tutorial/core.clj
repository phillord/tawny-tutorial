(ns tawny.tutorial.core
  (:require [tawny.owl :only save-ontology]
            [tawny.tutorial
             features hello onto-hello
             amino-acid-tree amino-acid-props
             amino-acid-pattern whats-in-a-name
             use-abc read-abc amino-acid-build
             autosave amino-acid-sio]))

(defonce output-file-path "./output/")

(defn save-ontology
  "'Overloads' save-ontology function."
  [o name type]
  (tawny.owl/save-ontology o (str output-file-path name) type))
  
(defn -main
  "Save ontologies in .omn and .owl format"
  []

  (save-ontology tawny.tutorial.features/o "features.omn" :omn)
  (save-ontology tawny.tutorial.features/o "features.owl" :owl)

  (save-ontology tawny.tutorial.onto-hello/hello "onto-hello.omn" :omn)
  (save-ontology tawny.tutorial.onto-hello/hello "onto-hello.owl" :owl)

  (save-ontology tawny.tutorial.amino-acid-tree/aa "amino-acid-tree.omn" :omn)
  (save-ontology tawny.tutorial.amino-acid-tree/aa "amino-acid-tree.owl" :owl)

  (save-ontology tawny.tutorial.amino-acid-props/aa "amino-acid-props.omn" :omn)
  (save-ontology tawny.tutorial.amino-acid-props/aa "amino-acid-props.owl" :owl)

  (save-ontology tawny.tutorial.amino-acid-pattern/aa "amino-acid-pattern.omn" :omn)
  (save-ontology tawny.tutorial.amino-acid-pattern/aa "amino-acid-pattern.owl" :owl)

  (save-ontology tawny.tutorial.whats-in-a-name/o "name-random-uuid.omn" :omn)
  (save-ontology tawny.tutorial.whats-in-a-name/o "name-random-uuid.owl" :owl)

  (save-ontology tawny.tutorial.whats-in-a-name/i "name-iri.omn" :omn)
  (save-ontology tawny.tutorial.whats-in-a-name/i "name-iri.owl" :owl)

  (save-ontology tawny.tutorial.whats-in-a-name/r "name-tawny-name.omn" :omn)
  (save-ontology tawny.tutorial.whats-in-a-name/r "name-tawny-name.owl" :owl)

  (save-ontology tawny.tutorial.whats-in-a-name/obo "name-obo.omn" :omn)
  (save-ontology tawny.tutorial.whats-in-a-name/obo "name-obo.owl" :owl)

  (save-ontology tawny.tutorial.whats-in-a-name/s "name-strings.omn" :omn)
  (save-ontology tawny.tutorial.whats-in-a-name/s "name-strings.owl" :owl)

  (save-ontology tawny.tutorial.use-abc/useabc "use-abc.omn" :omn)
  (save-ontology tawny.tutorial.use-abc/useabc "use-abc.owl" :owl)

  (save-ontology tawny.tutorial.read-abc/myABC "read-abc.omn" :omn)
  (save-ontology tawny.tutorial.read-abc/myABC "read-abc.owl" :owl)

  (save-ontology tawny.tutorial.amino-acid-build/aabuild "amino-acid-build.omn" :omn)
  (save-ontology tawny.tutorial.amino-acid-build/aabuild "amino-acid-build.owl" :owl)

  (save-ontology tawny.tutorial.amino-acid-sio/aasio "amino-acid-sio.omn" :omn)
  (save-ontology tawny.tutorial.amino-acid-sio/aasio "amino-acid-sio.owl" :owl)

  (println "The Tawny Tutorial is Installed and Ready"))
