(ns wordnik.test.core
  (:use [wordnik.core]
        [wordnik.api word account]
        [clojure.test]))

(def *wordnik-api-key* "<your api key here>")
(deftest bar
  (word-examples :word "funny" :api_key *wordnik-api-key*)
  (println (word :word "funny" :api_key *wordnik-api-key*))
  (println (word-definitions :word "funny" :api_key *wordnik-api-key*))
  (println (account-token-status :api_key *wordnik-api-key*))
  (is true true))