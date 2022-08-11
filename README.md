# rest-api

Simple rest api in clojure (Learning proposal)

3 simple endpoints + the not found

To do scheme:
 - id int
 - body string

/ GET -> receive all todos in json
/:id GET -> receive the todo with this id
/ POST -> create a new todo (body must have a valid todo)

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

```
lein run
```
