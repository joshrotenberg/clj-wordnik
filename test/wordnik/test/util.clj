(ns wordnik.test.util
  (:use [wordnik.util]
        [wordnik.test.properties]
        [clojure.test]))

(deftest lisp-to-camel-test
  (is (= "fooBarBaz" (lisp-to-camel "foo-bar-baz")))
  (is (= :fooBarBaz (lisp-to-camel :foo-bar-baz))))

(deftest transform-args-test
  (is (= {:fooBar "baz" :blehBoofDuf 20}
         (transform-args {:foo-bar "baz" :bleh-boof-duf 20}))))
  