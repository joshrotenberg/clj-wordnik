(ns wordnik.test.api.account
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api account]
        [wordnik.test.properties]
        [clojure.test]))

;; account
(def ^:dynamic *test-token* nil)

(deftest account-tests
  ;; test calls that don't require an auth token
  (with-api-key *wordnik-api-key*
    (is (= true (contains?
                 (authenticate :username *wordnik-username*
                               :password *wordnik-password*) :token)))
    (is (= true (contains?
                 (authenticate-post :username *wordnik-username*
                                    :body *wordnik-password*) :token)))
    (is (= true ((api-token-status) :valid)))
    ;; and calls that do
    (let [*test-token* (:token (authenticate-post
                                :username *wordnik-username*
                                :body *wordnik-password*))]
      (with-auth-token *test-token*
        (is (= *wordnik-username* (:userName  (user))))))))
;; see wordnik.test.api.wordlist for account-wordlists test


