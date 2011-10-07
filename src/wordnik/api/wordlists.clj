(ns wordnik.api.wordlists
  (:use [wordnik core]))

(defmacro def-wordnik-wordlists-method
  [name request-method & rest]
  (let [resource (str "wordLists.json")]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-wordlists-method wordlists :post
  :headers { :content-type "application-json"} )