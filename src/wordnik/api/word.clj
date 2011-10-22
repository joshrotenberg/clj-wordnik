(ns wordnik.api.word
  (:use wordnik.core))

(defmacro def-wordnik-word-method
  [name request-method action & rest]
  (let [resource (str "word.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-word-method word :get "{:word}")
(def-wordnik-word-method examples :get "{:word}/examples")
(def-wordnik-word-method definitions :get "{:word}/definitions")
(def-wordnik-word-method top-examples :get "{:word}/topExamples")
(def-wordnik-word-method punctuation-factor
  :get "{:word}/punctuationFactor")
(def-wordnik-word-method related :get "{:word}/related")
(def-wordnik-word-method pronunciations :get "{:word}/pronunciations")
(def-wordnik-word-method hyphenation :get "{:word}/hyphenation")
(def-wordnik-word-method frequency :get "{:word}/frequency")
(def-wordnik-word-method phrases :get "{:word}/phrases")
(def-wordnik-word-method audio :get "{:word}/audio")