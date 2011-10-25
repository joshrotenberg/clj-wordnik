(ns wordnik.api.wordlist
  (:use wordnik.core))

;; Functions for accessing the "wordList" calls.
;; The first argument is always the wordlist ID.
;;
;; (fetch "mywordlist")
;;
;; (delete "mysuckywordlist")
;;
;; (words "anotherwordlist" :limit 4 :sort-order "desc")
;;
;; See http://developer.wordnik.com/docs#!/wordlist for more information
;; and supported arguments. 
(defmacro def-wordnik-wordlist-method
  [name request-method action & rest]
  (let [resource (str "wordList.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

;; update - Update an existing wordlist
(def-wordnik-wordlist-method update :put "%s")

;; delete - Delete a wordlist
(def-wordnik-wordlist-method delete :delete "%s")

;; fetch - Fetch a wordlist
(def-wordnik-wordlist-method fetch :get "%s")

;; add-words - Add words to a wordlist
(def-wordnik-wordlist-method add-words :post "%s/words")

;; words - Fetch a wordlist's words
(def-wordnik-wordlist-method words :get "%s/words")

;; delete-words - Delete words from a wordlist
(def-wordnik-wordlist-method delete-words :post "%s/deleteWords")
