(ns wordnik.core
  (:use
   [clojure.data.json :as json]
   [clojure.set :as set :only [rename-keys]]
   [http.async.client.util :as requ]
   [http.async.client.request :as req]
   [http.async.client :as ac]
   [wordnik.util]
   ))


(def ^:dynamic *client-version* (System/getProperty "clj-wordnik.version"))
(def ^:dynamic *api-key* nil)
(def ^:dynamic *auth-token* nil)
(def ^:dynamic *api-url* "api.wordnik.com")
(def ^:dynamic *api-version* "v4")
(def ^:dynamic *protocol* "http")

(def memo-create-client (memoize ac/create-client))

(defn default-client []
  (memo-create-client :user-agent (str "clj-wordnik/" *client-version*)))

(defmacro with-api-key
  "Use the Wordnik API Key for the contained methods."
  [key & body]
  `(binding [*api-key* ~key]
     (do 
       ~@body)))

(defmacro with-auth-token
  "Use the Wordnik Auth Token, obtained by calling authenticate."
  [token & body]
  `(binding [*auth-token* ~token]
     (do  
       ~@body)))

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

(defn make-request [request-method uri arg-map auth-map]
  "Handles creating and sending the HTTP request and returns the response"
  (let [real-uri (subs-uri uri arg-map)
        body (:body arg-map) ;; get the post body
        headers (:headers arg-map)
        query-args (dissoc (merge arg-map auth-map) :body :headers)
        req (req/prepare-request request-method real-uri
                                 :query query-args
                                 :body body
                                 :headers headers)
        client (default-client) 
        res (apply req/execute-request client req
                   (apply concat (merge *default-callbacks*)))]
    (ac/await res)
    (json/read-json (ac/string res))))

(defmacro def-wordnik-method
  "Macro to create the Wordnik API calls"
  [name request-method path & rest]
  (let [rest-map (apply sorted-map rest)]
    `(defn ~name [& {:as args#}]
       (let [req-uri# (str *protocol* "://" *api-url* "/" *api-version*
                           "/" ~path)
             arg-map# (transform-args (merge ~rest-map args#))
             auth-map# (merge (when *api-key*
                                {:api_key *api-key*})
                              (when *auth-token*
                                {:auth_token *auth-token*}))]
         (make-request ~request-method
                       req-uri#
                       arg-map#
                       auth-map#)))))

