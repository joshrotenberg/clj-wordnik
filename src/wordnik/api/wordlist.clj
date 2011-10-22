(ns wordnik.api.wordlist
  (:use wordnik.core))

(defmacro def-wordnik-wordlist-method
  [name request-method action & rest]
  (let [resource (str "wordList.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-wordlist-method update :put "{:id}")
(def-wordnik-wordlist-method delete :delete "{:id}")
(def-wordnik-wordlist-method fetch :get "{:id}")
(def-wordnik-wordlist-method add-words :post "{:id}/words"
  :headers { :content-type "application/json"} )
(def-wordnik-wordlist-method words :get "{:id}/words")
(def-wordnik-wordlist-method delete-words
  :post "{:id}/deleteWords"
  :headers { :content-type "application/json"} )
