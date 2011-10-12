(ns wordnik.test.api.account
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api account]
        [wordnik.test.properties]
        [clojure.test]))

;; account
(def ^:dynamic *test-token* nil)
(println *wordnik-username*)

(deftest account-tests
  ;; test calls that don't require an auth token
  (with-api-key *wordnik-api-key*
    (is (= true (contains?
                 (account-authenticate :username *wordnik-username*
                                       :password *wordnik-password*) :token)))
    (is (= true (contains?
                 (account-authenticate-post :username *wordnik-username*
                                        :body *wordnik-password*) :token)))
    (is (= true ((account-api-token-status) :valid)))
    ;; and calls that do
    (let [*test-token* (:token (account-authenticate-post
                                :username *wordnik-username*
                                :body *wordnik-password*))]
      (with-auth-token *test-token*
        (is (= *wordnik-username* (:userName  (account-user))))
        (println (account-wordlists))))))
