(ns wordnik.api
  (:use
   [wordnik core]))

;; http://api.wordnik.com/v4/word.json/shoe
(defrecord ApiContext
    [^String protocol
     ^String host
     ^Integer version])

(def ^:dynamic *rest-api* (ApiContext. "http" "api.wordnik.com" 1))

