(ns wordnik.api.wordlist
  (:use [wordnik core]))

(defmacro def-wordnik-wordlist-method
  [name request-method action & rest]
  (let [resource (str "wordList.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-wordlist-method wordlist-update :put "{:wordlist-id}")
(def-wordnik-wordlist-method wordlist-delete :delete "{:wordlist-id}")
(def-wordnik-wordlist-method wordlist-fetch :get "{:wordlist-id}")
(def-wordnik-wordlist-method wordlist-add-words :post "{:wordlist-id}/words")
(def-wordnik-wordlist-method wordlist-fetch-words :get "{:wordlist-id}/words")
(def-wordnik-wordlist-method wordlist-delete-words
  :post "{:wordlist-id}/deleteWords")
