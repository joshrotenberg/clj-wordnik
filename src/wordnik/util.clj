(ns wordnik.util
  (:use [clojure.set :only [rename-keys]])
  (:require [clojure.string :as str]))

(defn local-join [parts]
  (apply str (first parts) (map str/capitalize (rest parts))))

(defn lisp-to-camel
  "Takes a Lisp style variable name and returns a camel case version, first
character lower, i.e. doof-cha-what-now becomse doofChaWhatNow"
  [var-name]
  (let [parts (str/split (name var-name) #"-")
        joined (local-join parts)]
    (if (keyword? var-name) (keyword joined) joined)))

(defn transform-args
  [arg-map]
  "Given a map of query args, renames using lisp-to-camel and replaces - with _
   for api-key and auth-token"
  (let [keys (keys arg-map)]
    (rename-keys arg-map (zipmap keys (map lisp-to-camel keys)))))
  
(defn status-is-server-error
  [status]
  "Return true if the HTTP status denotes a server error"
  (some #{status} (range 500 510)))

(defn status-is-client-error
  [status]
  "Return true if the HTTP status denotes a client error"
  (some #{status} (range 400 426)))