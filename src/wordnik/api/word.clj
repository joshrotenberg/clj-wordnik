(ns wordnik.api.word
  (:use wordnik.core))

;; Functions for accessing the "word" calls.
;; The first argument to these calls should be the word as a string, followed by
;; keyword param names and their arguments:
;;
;; (word "epidermolysis" :use-canonical true)
;;
;; (definitions "heterotypic")
;;
;; See http://developer.wordnik.com/docs#!/word for more information
;; and supported arguments. 
(defmacro def-wordnik-word-method
  [name request-method action & rest]
  (let [resource (str "word.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

;; examples - Returns examples for a word
(def-wordnik-word-method examples :get "%s/examples")

;; word - Given a word as a string, returns the WordObject that represents it
(def-wordnik-word-method word :get "%s")

;; definitions - Return definitions for a word
(def-wordnik-word-method definitions :get "%s/definitions")

;; top-examples - Returns a top example for a word
(def-wordnik-word-method top-example :get "%s/topExample")

;; related - Returns related words (thesaurus data) for a word
(def-wordnik-word-method related :get "%s/related")

;; pronunciations - Returns text pronunciations for a given word
(def-wordnik-word-method pronunciations :get "%s/pronunciations")

;; hyphenation - Returns syllable information for a word
(def-wordnik-word-method hyphenation :get "%s/hyphenation")

;; frequency - Returns word usage over time
(def-wordnik-word-method frequency :get "%s/frequency")

;; phrases - Fetches bi-gram phrases for a word
(def-wordnik-word-method phrases :get "%s/phrases")

;; audio - Fetches audio metadata for a word
(def-wordnik-word-method audio :get "%s/audio")