(ns wordnik.test.util
  (:use [wordnik.util]
        [clojure.test]))

(deftest lisp-to-camel-test
  (is (= "fooBarBaz" (lisp-to-camel "foo-bar-baz")))
  (is (= :fooBarBaz (lisp-to-camel :foo-bar-baz))))
