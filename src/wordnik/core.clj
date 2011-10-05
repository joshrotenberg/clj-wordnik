(ns wordnik.core
  (:use
   [clojure.data.json :as json]
   [clojure.set :as set :only [rename-keys]]
   [http.async.client.util :as requ]
   [http.async.client.request :as req]
   [http.async.client :as ac]
   [wordnik.util]
   ))

(def memo-create-client (memoize ac/create-client))

(defn default-client []
  (memo-create-client :user-agent "clj-wordnik/0.1.0-SNAPSHOT"))

(def ^:dynamic *api-key* nil)

(def ^:dynamic *api-url* "api.wordnik.com")
(def ^:dynamic *api-version* "v4")
(def ^:dynamic *protocol* "http")

(defmacro with-api-key
  "Use the Wordnik API Key for the contained methods."
  [key & body]
  `(binding [*api-key* ~key]
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
        query-args (dissoc (merge arg-map auth-map) :body) ;; and args w/o body
        req (req/prepare-request request-method real-uri
                                 :query query-args
                                 :body body)
        client (default-client) 
        res (apply req/execute-request client req
                   (apply concat (merge *default-callbacks*)))]
    (println query-args)
    (println (set/rename-keys query-args (zipmap (keys query-args) (map #(lisp-to-camel %) (keys query-args)))))
                   
    
    (ac/await res)
    (json/read-json (ac/string res))))

(defmacro def-wordnik-method
  "Macro to create the Wordnik API calls"
  [name request-method path & rest]
  (let [rest-map (apply sorted-map rest)]
    `(defn ~name [& {:as args#}]
       (let [req-uri# (str *protocol* "://" *api-url* "/" *api-version*
                           "/" ~path)
             arg-map# (merge ~rest-map args#)
             auth-map# (when *api-key*
                         {:api_key *api-key*})]
         (make-request ~request-method
                       req-uri#
                       arg-map#
                       auth-map#)))))

