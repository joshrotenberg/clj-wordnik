(ns wordnik.test.util
  (:use wordnik.util
        wordnik.test.properties
        clojure.test))

(deftest lisp-to-camel-test
  (is (= "fooBarBaz" (lisp-to-camel "foo-bar-baz")))
  (is (= :fooBarBaz (lisp-to-camel :foo-bar-baz))))

(deftest transform-args-test
  (is (= {:fooBar "baz" :blehBoofDuf 20}
         (transform-args {:foo-bar "baz" :bleh-boof-duf 20}))))

(deftest status-is-server-error-test
  (is (status-is-server-error 500))
  (is (status-is-server-error 502))
  (is (nil? (status-is-server-error 200))))

(deftest status-is-client-error-test
  (is (status-is-client-error 400))
  (is (status-is-client-error 401))
  (is (nil? (status-is-client-error 500))))

