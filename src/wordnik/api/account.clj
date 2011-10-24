(ns wordnik.api.account
  (:use wordnik.core))

(defmacro def-wordnik-account-method
  [name request-method action & rest]
  (let [resource (str "account.json/" action)]
    `(def-wordnik-method ~name ~request-method ~resource ~@rest)))

(def-wordnik-account-method authenticate
  :get "authenticate/%s")
(def-wordnik-account-method authenticate-post
  :post "authenticate/%s")
(def-wordnik-account-method wordlists :get "wordLists")
(def-wordnik-account-method api-token-status :get "apiTokenStatus")
(def-wordnik-account-method user :get "user")
