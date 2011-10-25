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

(def user-agent (str "clj-wordnik/" *client-version*))

;; Run the enclosed call(s) with the API key
(defmacro with-api-key
  "Use the Wordnik API Key for the contained methods."
  [key & body]
  `(binding [*api-key* ~key]
     (do 
       ~@body)))

;; Run the enclosed call(s) with the auth token (obtained from calling
;; authenticate.
(defmacro with-auth-token
  "Use the Wordnik Auth Token, obtained by calling authenticate."
  [token & body]
  `(binding [*auth-token* ~token]
     (do  
       ~@body)))

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
       (json/read-json (:body response))))))

(defn prepare-request [request-method uri first-arg arg-map auth-map]
  "Prepares the HTTP request"
  (let [real-uri (format uri first-arg)
        body (:body arg-map) ;; get the post body
        query-args (dissoc (merge arg-map auth-map) :body)]
    {:method request-method
     :url real-uri
     :query-params query-args
     :content-type :json
     :headers {"User-Agent" user-agent}
     :body body}))

(defmacro def-wordnik-method
  "Macro to create the Wordnik API calls"
  [name request-method path & rest]
  `(defn ~name [& args#];;& {:as args#}]
     (let [req-uri# (str "http://" *api-url* "/" *api-version*
                         "/" ~path)
           split-args# (split-with (complement keyword?) args#)
           first-arg# (ffirst split-args#)
           arg-map# (transform-args (apply hash-map (second split-args#)))
           auth-map# (merge (when *api-key*
                              {:api_key *api-key*})
                            (when *auth-token*
                              {:auth_token *auth-token*}))
           request# (prepare-request ~request-method
                                     req-uri#
                                     first-arg#
                                     arg-map#
                                     auth-map#)]
         (execute-request request#))))

