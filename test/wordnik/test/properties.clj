(ns wordnik.test.properties
  (:import (java.util.Properties)))


;; slurp in properties
(def ^:dynamic *props*
  (into {} (doto (java.util.Properties.)
             (.load (-> (Thread/currentThread)
                       (.getContextClassLoader)
                       (.getResourceAsStream "test.properties"))))))

(def ^:dynamic  *wordnik-api-key*
  (or (get *props* "wordnik.api.key")
      (throw (Exception. "supply your Wordnik API key in resources/test.properties to run the tests"))))

(def ^:dynamic  *wordnik-username*
  (or (get *props* "wordnik.username")
      (throw (Exception. "supply your Wordnik username in resources/test.properties to run the tests"))))

(def ^:dynamic  *wordnik-password*
  (or (get *props* "wordnik.password")
      (throw (Exception. "supply your Wordnik password in resources/test.properties to run the tests"))))
