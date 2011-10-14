(ns wordnik.api.wordlist
  (:use [wordnik core]))

(defmacro def-wordnik-wordlist-method
  [name request-method action & rest]
  (let [resource (str "wordList.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-wordlist-method wordlist-update :put "{:id}")
(def-wordnik-wordlist-method wordlist-delete :delete "{:id}")
(def-wordnik-wordlist-method wordlist-fetch :get "{:id}")
(def-wordnik-wordlist-method wordlist-add-words :post "{:id}/words"
  :headers { :content-type "application/json"} )
(def-wordnik-wordlist-method wordlist-words :get "{:id}/words")
(def-wordnik-wordlist-method wordlist-delete-words
  :post "{:wordlist-id}/deleteWords")
