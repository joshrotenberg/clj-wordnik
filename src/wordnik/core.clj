(ns wordnik.core
  (:use
   [clojure.data.json :as json]
   [http.async.client.util :as requ]
   [http.async.client.request :as req]
   [http.async.client :as ac]
  ))

(def memo-create-client (memoize ac/create-client))

(defn default-client []
  (memo-create-client :user-agent "clj-wordnik/0.1.0"))

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
        req (req/prepare-request :get real-uri
                                 :query {:api_key (:api_key arg-map)})

        client (default-client) 
        res (apply req/execute-request client req
                   (apply concat (merge *default-callbacks*)))]
    (println real-uri arg-map)
    (ac/await res)
    (ac/string res)))

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

