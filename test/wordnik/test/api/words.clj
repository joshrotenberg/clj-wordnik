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
    (is (= true (contains? (wotd) :word)))
    (is (= true (contains? (search "ostracize") :searchResults)))
    (is (= 4 (count (random-words :limit 4))))
    (is (= true (contains? (random-word) :word)))
    ))
  
  