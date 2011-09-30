(ns wordnik.api.word
  (:use [wordnik core]))

(defmacro def-wordnik-word-method
  [name request-method action & rest]
  (let [resource (str "word.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))
;; http://api.wordnik.com/v4/word.json/box/examples

(def-wordnik-word-method word :get "{:word}")
(def-wordnik-word-method word-examples :get "{:word}/examples")
(def-wordnik-word-method word-definitions :get "{:word}/definitions")
(def-wordnik-word-method word-top-examples :get "{:word}/topExamples")
(def-wordnik-word-method word-punctuation-factor :get "{:word}/punctuationFactor")
(def-wordnik-word-method word-related :get "{:word}/related")
