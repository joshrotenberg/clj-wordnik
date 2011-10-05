(ns wordnik.test.core
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api word account]
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
                                                 :partOfSpeech "adjective"
                                                 :type "antonym")
                                   first
                                   :words)]
                  (seq-contains? my-words "small"))))
    (is (= true (contains? (first (word-pronunciations :word "route")) :id)))
    (is (= 11 (count (word-hyphenation :word "antidisestablishmentarianism"))))
    (is (= 0 (-> (word-frequency :word "software"
                                 :startYear 1806
                                 :endYear 1806)
                 :frequency
                 first
                 :count)))
    (is (= true (seq-contains? (map #(:gram1 %) (word-phrases :word "lot"))
                               "parking")))
    (is (= true  (contains? (first (word-audio :word "scout" :limit 1)) :fileUrl)))
    ))


(deftest account-tests
  (with-api-key *wordnik-api-key*
    (println (account-authenticate :username *wordnik-username*
                                   :password *wordnik-password*))
    (println (account-authenticate-post :username *wordnik-username*
                                        :body *wordnik-password*))))


