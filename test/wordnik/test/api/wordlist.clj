(ns wordnik.test.api.wordlist
  (:use wordnik.core
        wordnik.util
        [wordnik.api account wordlist wordlists]
        wordnik.test.properties
        re-rand
        [clojure.data.json :as json]
        clojure.data
        clojure.test))

;; wordlist
(def test-wordlist {:name (re-rand #"[A-Za-z0-9]{20}")
                    :type "PUBLIC"
                    :description "test-list"
                    :username *wordnik-username*})

(def test-words [ {:word "chair"}
                  {:word "table"} ])

(deftest wordlist-test
  (with-api-key *wordnik-api-key*
    (let [token (:token (account-authenticate-post
                         :username *wordnik-username*
                         :body *wordnik-password*))]
      (with-auth-token token
        (let [result (wordlists :body (json/json-str test-wordlist))
              wordlist-name (:name result)]
          ;; test our local object agains the one we created
          (is 4 (count (nth (diff test-wordlist result) 2)))
          ;; now fetch it, and test it again
          (is 4 (count (nth (diff result (wordlist-fetch :id wordlist-name)) 2)))

          (wordlist-add-words :id wordlist-name
                              :body (json/json-str test-words))
          (is (>= (count (account-wordlists)) 1))
          (is 2 (count (wordlist-words :id wordlist-name)))
          (wordlist-delete-words :id wordlist-name
                                 :body (json/json-str [{:word "chair"}]))
          (is 0 (count (wordlist-words :id wordlist-name)))
          (wordlist-delete :id wordlist-name)
          )))))
    