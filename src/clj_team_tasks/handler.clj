(ns clj-team-tasks.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

;;------------------------------------------------------------------------------------------------
(comment
  (require 'alembic.still)
  (alembic.still/load-project)

  (>sdoc)

  (require '[ring.adapter.jetty :as jetty])
  (defonce server (jetty/run-jetty #'app {:port 8080 :join? false}))

  )
