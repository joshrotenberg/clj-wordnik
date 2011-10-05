(ns wordnik.util
  (:use [clojure.contrib.str-utils2 :as s :only [split capitalize map-str]])
  (:use [clojure.contrib.string :as cs :only [replace-char]]))

;; from stack overflow: http://bit.ly/qnogIl
(defn seq-contains?
  "Determine whether a sequence contains a given item"
  [sequence item]
  (if (empty? sequence)
    false
    (reduce #(or %1 %2) (map #(= %1 item) sequence))))

(defn- local-join
  [parts]
  (let [f (first parts)
        n (next parts)]
    (str f (s/map-str s/capitalize n))))

(defn lisp-to-camel
  "Takes a Lisp style variable name and returns a camel case version, first
character lower, i.e. doof-cha-what-now becomse doofChaWhatNow"
  [var-name]
  (let [pattern (re-pattern "\\-")
        parts (s/split (name var-name) pattern)
        is-keyword? (keyword var-name)]
    (if (keyword? var-name)
      (keyword (local-join parts))
      (local-join parts))))


