(ns wordnik.api.word
  (:use [wordnik core]))

(defmacro def-wordnik-word-method
  [name request-method action & rest]
  (let [resource (str "word.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-word-method word :get "{:word}")
(def-wordnik-word-method word-examples :get "{:word}/examples")
(def-wordnik-word-method word-definitions :get "{:word}/definitions")
(def-wordnik-word-method word-top-examples :get "{:word}/topExamples")
(def-wordnik-word-method word-punctuation-factor
  :get "{:word}/punctuationFactor")
(def-wordnik-word-method word-related :get "{:word}/related")
(def-wordnik-word-method word-pronunciations :get "{:word}/pronunciations")
(def-wordnik-word-method word-hyphenation :get "{:word}/hyphenation")
(def-wordnik-word-method word-frequency :get "{:word}/frequency")
(def-wordnik-word-method word-phrases :get "{:word}/phrases")
(def-wordnik-word-method word-audio :get "{:word}/audio")