(ns wordnik.test.api.word
  (:use wordnik.core
        wordnik.util
        wordnik.api.word
        wordnik.test.properties
        clojure.test)
  (:require [clj-http.client :as client]))

(deftest word-tests
  (with-api-key  *wordnik-api-key*
    (is (= (word "cats" :use-canonical "false" :api-key "key")
           (word "cats" :use-canonical false :api-key "key")))
    (is (= "funny" (:word (word "funny"))))
    (is (= "stupid" (:canonicalForm (word "stupid"))))
    (is (= "ugly" (-> (examples "ugly")
                      :examples
                      first
                      :word)))
    (is (= "small" (some #{"small"}
                         (-> (related "big"
                                      :part-of-speech "adjective"
                                      :type "antonym")
                             first
                             :words))))
    (is (= true (contains? (first (pronunciations "route")) :id)))
    (is (= 11 (count (hyphenation "antidisestablishmentarianism"))))
    (is (= 0 (-> (frequency "software"
                            :start-year 1806
                            :end-year 1806)
                 :frequency
                 first
                 :count)))
    (is (= "parking"  (some #{"parking"} (map :gram1 (phrases "lot")))))
    (is (= true  (contains? (first (audio "scout" :limit 1)) :fileUrl)))))

