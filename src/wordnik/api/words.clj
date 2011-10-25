(ns wordnik.api.words
  (:use wordnik.core))

;; Functions for accessing the "words" calls.
;; The first argument to search should be the word to search for.
;;
;; (search "dripstones")
;;
;; See http://developer.wordnik.com/docs#!/words for more information
;; and supported arguments. 
(defmacro def-wordnik-words-method
  [name request-method action & rest]
  (let [resource (str "words.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

;; search - Searches words
(def-wordnik-words-method search :get "search/%s")

;; wotd - Returns a specific WordOfTheDay
(def-wordnik-words-method wotd :get "wordOfTheDay")

;; random-words - Returns an array of random words
(def-wordnik-words-method random-words :get "randomWords")

;; random-word - Returns a random word
(def-wordnik-words-method random-word :get "randomWord")
