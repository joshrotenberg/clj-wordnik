(ns wordnik.api.wordlist
  (:use wordnik.core))

(defmacro def-wordnik-wordlist-method
  [name request-method action & rest]
  (let [resource (str "wordList.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-wordlist-method update :put "%s")
(def-wordnik-wordlist-method delete :delete "%s")
(def-wordnik-wordlist-method fetch :get "%s")
(def-wordnik-wordlist-method add-words :post "%s/words")
;;  :headers { :content-type "application/json"} )
(def-wordnik-wordlist-method words :get "%s/words")
(def-wordnik-wordlist-method delete-words :post "%s/deleteWords")
;;  :headers { :content-type "application/json"} )
