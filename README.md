# clj-wordnik

A Clojure client for [Wordnik](http://www.wordnik.com)'s API.

## Docs

See the uberdoc in docs, generated by
[Marginalia](https://github.com/fogus/marginalia).

For the most part API calls follow the methods found on
[Wordnik](http://developer.wordnik.com/docs)'s API documentation, with
a few exceptions. Most calls are simply the call name and then the
params as keywords:

```clojure
(word-examples :word "vinculum" :include-duplicates false :api-key "key") 
```

All call parameters can use the more lispy '-'
separated style and they will be converted to camel case
automatically, for example the word-examples call has a 'useCanonical'
param, but in clj-wordnik this can be specified as 'use-canonical'.

You can specify the api-key as a parameter directly, or use the
with-api-key call to group multiple calls together without having to
specify it for each one. You can also do the same with with-auth-token
for calls that require it.

## Usage

in your project.clj

```
[clj-wordnik "0.1.0-SNAPSHOT"]
```

```clojure
(ns your.app
    (:use wordnik.core
          [wordnik.api word])
)
;; get your api key from somewhere

(def my-api-key "<your wordnik api key here>")

;; look up the word discombobulated
(with-api-key my-api-key
    (let [discombobulated (word :word "discombobulated")]
    	 ;; do stuff with discombobulated
))
```
## Testing

You'll need to specify your Wordnik API key, username and password in
the resources/test.properties file to run the tests.

## Examples

For now just see the [unit tests](https://github.com/joshrotenberg/clj-wordnik/tree/master/test/wordnik/test/api).

## Status/TODO

# all api calls are supported
# nuke obvious args like :word in favor of a required first position arg or something
# write more docs
# write more tests

## Credits

Lots of inspiration from [Adam Wynne/Stream Science](https://github.com/adamwynne)'s [twitter-api](https://github.com/adamwynne/twitter-api).

[Raynes](https://github.com/Raynes) put a clj-wordnik up just after mine, so we decided to work together to fight for wordless people everywhere. 

## License

Copyright (C) 2011 Josh Rotenberg

Distributed under the Eclipse Public License, the same as Clojure.
