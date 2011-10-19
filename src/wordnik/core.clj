(ns wordnik.core
  (:use
   [clojure.data.json :as json]
   [clojure.set :as set :only [rename-keys]]
   [wordnik.util])
  (:require [clj-http.client :as cclient]))

(def ^:dynamic *client-version* (System/getProperty "clj-wordnik.version"))
(def ^:dynamic *api-key* nil)
(def ^:dynamic *auth-token* nil)

(def ^:dynamic *api-url* "api.wordnik.com")
(def ^:dynamic *api-version* "v4")

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

(defn execute-request [request]
  "Executes the HTTP request and handles the response"
  (let [response (cclient/request request)
        status (:status response)
        body (:body response)
        headers (:headers response)]
    (cond
     (status-is-client-error status) (throw (Exception. "Client error"))
     (status-is-server-error status) (throw (Exception. "Server error"))
     :else
     (when-not (empty? body)
       (json/read-json (:body response)))
     )))

(defn prepare-request [request-method uri arg-map auth-map]
  "Prepares the HTTP request"
  (let [real-uri (subs-uri uri arg-map)
        body (:body arg-map) ;; get the post body
        headers (:headers arg-map)
        query-args (dissoc (merge arg-map auth-map) :body :headers)
        request {:method request-method
                 :url real-uri
                 :query-params query-args
                 :content-type :json
                 :body body}]
    (execute-request request)))

(defmacro def-wordnik-method
  "Macro to create the Wordnik API calls"
  [name request-method path & rest]
  (let [rest-map (apply sorted-map rest)]
    `(defn ~name [& {:as args#}]
       (let [req-uri# (str "http://" *api-url* "/" *api-version*
                           "/" ~path)
             arg-map# (transform-args (merge ~rest-map args#))
             auth-map# (merge (when *api-key*
                                {:api_key *api-key*})
                              (when *auth-token*
                                {:auth_token *auth-token*}))]
         (prepare-request ~request-method
                          req-uri#
                          arg-map#
                          auth-map#)))))

