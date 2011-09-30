(ns wordnik.test.core
  (:use [wordnik.core]
        [wordnik.api.word]
        [clojure.test]))

(def api-key "3348c2e6bd192e189d004095a5500d2fb705419146533e2dc")
(deftest bar
  (word-examples :word "funny")
  (word :word "chubby")
  (word-definitions :word "super")
  (is true true))