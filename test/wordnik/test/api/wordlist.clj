(ns wordnik.test.api.wordlist
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api wordlist]
        [wordnik.test.properties]
        [clojure.test]))

;; wordlist
(comment
(deftest wordlist-test
  (with-api-key *wordnik-api-key*
    (let [token (:token (account-authenticate-post
                         :username *wordnik-username*
                         :body *wordnik-password*))]
      (with-auth-token token
        (println (wordlist-fetch :id "my-test-list--2"))
        (println (wordlist-words :id "my-test-list--2"))
        (println (wordlist-add-words :id "my-test-list--2"
                                     :username *wordnik-username*
                                     :body "[\"buh\", \"doof\"]")))))))