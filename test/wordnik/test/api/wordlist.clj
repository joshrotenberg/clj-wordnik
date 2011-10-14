(ns wordnik.test.api.wordlist
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api account wordlist wordlists]
        [wordnik.test.properties]
        [re-rand]
        [clojure.data.json :as json]
        [clojure.data]
        [clojure.test]))

;; wordlist
(def test-wordlist {:name (re-rand #"[A-Za-z0-9]{20}")
                    :type "PUBLIC"
                    :description "test-list"
                    :username *wordnik-username*})

(def test-words [ {:stringvalue "chair"}
                  {:stringvalue "table"} ])
(println (json/json-str test-words))
(deftest wordlist-test
  (with-api-key *wordnik-api-key*
    (let [token (:token (account-authenticate-post
                         :username *wordnik-username*
                         :body *wordnik-password*))]
      ;;(println token)
      (with-auth-token token
        (let [result (wordlists :body (json/json-str test-wordlist))
              wordlist-name (:name result)]
          ;; test our local object agains the one we created
          (is 4 (count (nth (diff test-wordlist result) 2)))
          ;; now fetch it, and test it again
          (is 4 (count (nth (diff result (wordlist-fetch :id wordlist-name)) 2)))
          (println (wordlist-add-words :id wordlist-name
                                       :body "chair,table"));;(json/json-str test-words)))
          )

        ;;(println (wordlist-words :id "my-test-list--2"))
        ;;(println (wordlist-add-words :id "my-test-list--2"
        ;;:username *wordnik-username*
        ;;:body "[\"buh\", \"doof\"]"))
        ))))
    