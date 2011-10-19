(ns wordnik.util
  (:use [clojure.contrib.str-utils2 :as s :only [split capitalize map-str]])
  (:use [clojure.contrib.string :as cs :only [replace-char]])
  (:use [clojure.set :as set :only [rename-keys]]))

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

(defn transform-args
  [arg-map]
  "Given a map of query args, renames using lisp-to-camel and replaces - with _ for api-key and auth-token"
  (set/rename-keys arg-map (zipmap (keys arg-map) (map #(lisp-to-camel %) (keys arg-map)))))
  
(defn status-is-server-error
  [status]
  "Return true if the HTTP status denotes a server error"
  (seq-contains? (range 500 510) status))

(defn status-is-client-error
  [status]
  "Return true if the HTTP status denotes a client error"
  (seq-contains? (range 400 426) status))