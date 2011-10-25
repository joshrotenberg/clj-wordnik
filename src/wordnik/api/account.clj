(ns wordnik.api.account
  (:use wordnik.core))

;; Functions for accessing the "account" calls.
;; The first argument to both authenticate calls should be
;; the username as a string.
;;
;; (authenticate "picklelover" :password "secret123")
;;
;; (authenticate-post "matilda" :body "secretabc")
;;
;; See http://developer.wordnik.com/docs#!/account for more information
;; and supported arguments. 
(defmacro def-wordnik-account-method
  [name request-method action & rest]
  (let [resource (str "account.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

;; authenticate - Authenticate a user
(def-wordnik-account-method authenticate
  :get "authenticate/%s")

;; authenticate - Authenticate a user, send the password as the post body
(def-wordnik-account-method authenticate-post
  :post "authenticate/%s")

;; wordlists - Fetches WordList objects for the logged-in user
(def-wordnik-account-method wordlists :get "wordLists")

;; api-token-status - Returns usage statistics for the API account
(def-wordnik-account-method api-token-status :get "apiTokenStatus")

;; user - Returns the logged-in user
(def-wordnik-account-method user :get "user")
