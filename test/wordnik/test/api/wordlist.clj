(ns wordnik.test.api.wordlist
  (:use wordnik.core
        wordnik.util
        wordnik.test.properties
        re-rand
        [clojure.data.json :as json]
        clojure.data
        clojure.test)
  (:require
   [wordnik.api.account :as account]
   [wordnik.api.wordlist :as wl]
   [wordnik.api.wordlists :as wls]
            ))

;; wordlist
(def test-wordlist {:name (re-rand #"[A-Za-z0-9]{20}")
                    :type "PUBLIC"
                    :description "test-list"
                    :username *wordnik-username*})

(def test-words [ {:word "chair"}
                  {:word "table"} ])

(deftest wordlist-test
  (with-api-key *wordnik-api-key*
    (let [token (:token (account/authenticate-post *wordnik-username*
                         :body *wordnik-password*))]
      (with-auth-token token
        (let [result (wls/wordlists :body (json/json-str test-wordlist))
              wordlist-name (:name result)]
          ;; test our local object agains the one we created
          (is 4 (count (nth (diff test-wordlist result) 2)))
          ;; now fetch it, and test it again
          (is 4 (count (nth (diff result (wl/fetch wordlist-name)) 2)))

          (wl/add-words wordlist-name
                     :body (json/json-str test-words))
          ;;(is (>= (count (account/wordlists)) 1))
          (is 2 (count (wl/words wordlist-name)))
          (wl/delete-words wordlist-name
                        :body (json/json-str [{:word "chair"}]))
          (is 0 (count (wl/words wordlist-name)))
          (wl/delete wordlist-name)
          )))))
    