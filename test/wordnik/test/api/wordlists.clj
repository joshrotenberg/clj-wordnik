(ns wordnik.test.api.wordlists
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api wordlists]
        [wordnik.test.properties]
        [clojure.data.json :as json]
        [clojure.test]))

;; wordlists
(def wordlist
  (json/json-str {:name "bowl" :type "PUBLIC"
                  :description "test-list"
                  :username *wordnik-username* }))
(comment
(deftest wordlists-tests
  (with-api-key *wordnik-api-key*
    (let [token (:token (account-authenticate :username *wordnik-username*
                                              :password *wordnik-password*))]
      (with-auth-token token
        (println (wordlists :body wordlist))))))
)
