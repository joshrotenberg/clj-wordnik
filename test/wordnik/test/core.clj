(ns wordnik.test.core
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api word account wordlists words]
        [clojure.test])
  (:import (java.util.Properties)))

;; slurp in properties
(def ^:dynamic *props*
  (into {} (doto (java.util.Properties.)
             (.load (-> (Thread/currentThread)
                       (.getContextClassLoader)
                       (.getResourceAsStream "test.properties"))))))

;; set the api key
(def ^:dynamic  *wordnik-api-key*
  (or (get *props* "wordnik.api.key")
      (throw (Exception. "supply your Wordnik API key in resources/test.properties to run the tests"))))

(def ^:dynamic  *wordnik-username*
  (or (get *props* "wordnik.username")
      (throw (Exception. "supply your Wordnik username in resources/test.properties to run the tests"))))

(def ^:dynamic  *wordnik-password*
  (or (get *props* "wordnik.password")
      (throw (Exception. "supply your Wordnik password in resources/test.properties to run the tests"))))

(deftest word-tests
  (with-api-key  *wordnik-api-key*
    (is (= "funny" (:word (word :word "funny"))))
    (is (= "stupid" (:canonicalForm (word :word "stupid"))))
    (is (= "ugly" (-> (word-examples :word "ugly")
                      :examples
                      first
                      :word)))
    
    (is (= true (let [my-words (-> (word-related :word "big"
                                                 :part-of-speech "adjective"
                                                 :type "antonym")
                                   first
                                   :words)]
                  (seq-contains? my-words "small"))))
    (is (= true (contains? (first (word-pronunciations :word "route")) :id)))
    (is (= 11 (count (word-hyphenation :word "antidisestablishmentarianism"))))
    (is (= 0 (-> (word-frequency :word "software"
                                 :start-year 1806
                                 :end-year 1806)
                 :frequency
                 first
                 :count)))
    (is (= true (seq-contains? (map #(:gram1 %) (word-phrases :word "lot"))
                               "parking")))
    (is (= true  (contains? (first (word-audio :word "scout" :limit 1)) :fileUrl)))
    ))

(def ^:dynamic *test-token* nil)

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

;;(comment
;;  (deftest wordlists-tests
;;    (with-api-key *wordnik-api-key*
;;      (let [token (:token (account-authenticate :username *wordnik-username*
;;                                                :password *wordnik-password*))]
;;        (with-auth-token token
;;          (println (wordlists :body hexml)))
;;        ))))

(deftest words-tests
  (with-api-key *wordnik-api-key*
    (println (first (words-search :query "bowl")))))