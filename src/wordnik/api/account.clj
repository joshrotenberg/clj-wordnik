(ns wordnik.api.account
  (:use [wordnik core]))

(defmacro def-wordnik-account-method
  [name request-method action & rest]
  (let [resource (str "account.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-account-method account-authenticate
  :get "authenticate/{:username}")
(def-wordnik-account-method account-authenticate-post
  :post "authenticate/{:username}")
(def-wordnik-account-method account-wordlists :get "wordLists")
(def-wordnik-account-method account-api-token-status :get "apiTokenStatus")
(def-wordnik-account-method account-user :get "user")
