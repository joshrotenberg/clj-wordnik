(ns wordnik.test.api.words
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api words]
        [wordnik.test.properties]
        [clojure.test]))

;; words-wotd
;; words-search
;; words-random-words
;; words-random-word
(deftest words-tests
  (with-api-key *wordnik-api-key*
    (is (= true (contains? (words-wotd) :word)))
    (is (= true (contains? (words-search :word "ostracize") :searchResults)))
    (is (= 4 (count (words-random-words :limit 4))))
    (is (= true (contains? (words-random-word) :word)))
    
    ))
  
  