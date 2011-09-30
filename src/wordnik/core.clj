(ns wordnik.core
  (:require
   [clojure.data.json :as json]
   [http.async.client :as ac]
   [http.async.client.request :as acr])
  )

(defn get-client []
  (ac/create-client))

(def get-client-memo (memoize get-client))
(def ^:dynamic *api-url* "api.wordnik.com")
(def ^:dynamic *api-version* "v4")
(def ^:dynamic *protocol* "http")

;; from twitter-api
(defn subs-uri
  "substitutes parameters for tokens in the uri"
  [uri params]
  (loop [matches (re-seq #"\{\:(\w+)\}" uri)
         ^String result uri]
    (if (empty? matches) result
        (let [[token kw] (first matches)
              value (get params (keyword kw))]
          (if-not value (throw (Exception. (format "%s needs :%s param to be supplied" uri kw))))
          (recur (rest matches) (.replace result token (str value)))))))

(defn make-request [request-method uri arg-map]
  (let [real-uri (subs-uri uri arg-map)
        req (acr/prepare-request get-client-memo
                                 :get "http://google.com"
                                 :headers {:my-header "value"})]
    ;;req (acr/prepare-request get-client-memo :action real-uri)]
    (println real-uri)))

(defmacro def-wordnik-method
  "Macro to create the Wordnik API calls"
  [name request-method path & rest]
  (let [rest-map (apply sorted-map rest)]
    `(defn ~name [& {:as args#}]
       (let [req-uri# (str *protocol* "://" *api-url* "/" *api-version*
                           "/" ~path)
             arg-map# (merge ~rest-map args#)]
         (make-request ~request-method
                       req-uri#
                       arg-map#)))))

;; account
;; word
;; word-list
;; word-lists
;; words
;;(def-wordnik-method word-oof :get "word.json")

(comment (defn word [w key]
  (with-open [client (get-client-memo)]
    (let [res (ac/GET client (str "http://api.wordnik.com/v4/word.json/" w)
                      :query {:api_key key})]
      (ac/await res)
      (ac/string res)))))