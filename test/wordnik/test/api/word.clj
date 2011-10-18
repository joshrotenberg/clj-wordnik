(ns wordnik.test.api.word
  (:use [wordnik.core]
        [wordnik.util]
        [wordnik.api word]
        [wordnik.test.properties]
        [clojure.test]))

(deftest word-tests
  (with-api-key  *wordnik-api-key*
    (is (= (word :word "cats" :use-canonical "false" :api-key "key")
           (word :word "cats" :use-canonical false :api-key "key")))
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

