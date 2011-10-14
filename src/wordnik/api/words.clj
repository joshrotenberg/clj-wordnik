(ns wordnik.api.words
  (:use [wordnik core]))

(defmacro def-wordnik-words-method
  [name request-method action & rest]
  (let [resource (str "words.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-words-method words-wotd :get "wordOfTheDay")
(def-wordnik-words-method words-search :get "search/{:word}")
(def-wordnik-words-method words-random-words :get "randomWords")
(def-wordnik-words-method words-random-word :get "randomWord")
