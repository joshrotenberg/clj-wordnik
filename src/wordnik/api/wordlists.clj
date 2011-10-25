(ns wordnik.api.wordlists
  (:use wordnik.core))

;; Functions for accessing the "wordlists" call.
;;
;; See http://developer.wordnik.com/docs#!/wordlists for more information
;; and supported arguments. 
(defmacro def-wordnik-wordlists-method
  [name request-method & rest]
  (let [resource (str "wordLists.json")]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

;; wordlists - Create a wordlist
(def-wordnik-wordlists-method wordlists :post)
