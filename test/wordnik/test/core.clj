(ns wordnik.test.core
  (:use [wordnik.core]
        [wordnik.api word account]
        [clojure.test]))

(
(deftest word-tests
  (is "funny" (:word (word :word "funny" :api_key *wordnik-api-key*)))
  (is "stupid" (:canonicalForm (word :word "stupid" :api_key *wordnik-api-key*)))
  (println (:examples (word-examples :word "ugly" :api_key *wordnik-api-key*)))
  (is "ugly" (:word (word-examples :word "ugly" :api_key *wordnik-api-key*)))
  )

(comment
  (println word-examples :word "funny" :api_key *wordnik-api-key*)
  
  (println (word-definitions :word "funny" :api_key *wordnik-api-key*))
  (println (account-token-status :api_key *wordnik-api-key*))
  (is true true))


