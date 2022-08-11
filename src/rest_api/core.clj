(ns rest-api.core
  (:use ring.adapter.jetty)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

; defining the struct
(defstruct todo :id :body)

; Get the ID from the struct
(def get-id (accessor todo :id))

; Converts hash-map to the todo struct
(defn h_to_s [td]
  (let [id (get td "id" nil)
        body (get td "body" nil)]
    (if (or (= id nil) (= body nil) )
      nil
      (struct todo id body)
    )
  ))

; Verify is the input is of type todo
(defn todo? [td]
  (if (nil? td)
    false
    (let [id (get-in td [:id] nil)
          body (get-in td [:body] nil)]
      (if (and (number? id) (string? body))
        true
        false
      )
    )))

; Todo vector mutable to store all my todos
(def todos (atom [(struct todo 1 "Initial TODO")]))

; Get a single todo by id from the todos list
(defn get-one [id td-list]
  (if (> (count td-list) 0)
    (let [td (peek td-list)]
        (if (= (get-id td) id)
          {:data td}
          (get-one id (pop td-list)))
    )
    {:message "Not Found"}
  ))

; Append new todo into the todos list
(defn add-one [td] (swap! todos conj td))

; Create endpoint
; Create a new todo
(defn create-one [request]
  ; this td is a hash-map
  (let [body (get-in request [:body])
        td (h_to_s body)] 
    ; we will need to convert to the struct
    (if (todo? td)
      (do
       (add-one td)
       (response {:data td})
      )
      (response {:message "Not Created"})
    )
  )
)

(defroutes app-routes
  ; Get all todos
  (GET "/" [] (response {:data @todos}))

  ; Get a single todo
  (GET "/:id" [id] (response (get-one (Integer/parseInt id) @todos)))

  ; Create a new todo
  (POST "/" [] create-one)

  ; Not found route
  (route/not-found (response {:message "Not Found"})))

(def app
  (wrap-json-response (wrap-json-body  app-routes)))

(defn -main
  [& args]
  (run-jetty app {:port 8000}))
