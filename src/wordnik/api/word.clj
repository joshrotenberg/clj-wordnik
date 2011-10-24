(ns wordnik.api.word
  (:use wordnik.core))

(defmacro def-wordnik-word-method
  [name request-method action & rest]
  (let [resource (str "word.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-word-method word :get "%s")
(def-wordnik-word-method examples :get "%s/examples")
(def-wordnik-word-method definitions :get "%s/definitions")
(def-wordnik-word-method top-examples :get "%s/topExamples")
(def-wordnik-word-method punctuation-factor
  :get "%s/punctuationFactor")
(def-wordnik-word-method related :get "%s/related")
(def-wordnik-word-method pronunciations :get "%s/pronunciations")
(def-wordnik-word-method hyphenation :get "%s/hyphenation")
(def-wordnik-word-method frequency :get "%s/frequency")
(def-wordnik-word-method phrases :get "%s/phrases")
(def-wordnik-word-method audio :get "%s/audio")