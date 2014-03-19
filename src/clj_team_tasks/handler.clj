(ns clj-team-tasks.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [net.cgrand.enlive-html :as html]))


(html/deftemplate index-page "templates/index.html" [])
;;------------------------------------------------------------------------------------------------

(defroutes app-routes
  (GET "/" [] (reduce str (index-page)))
  (GET "/register" [] "Found")
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
  (def server (jetty/run-jetty #'app {:port 8080 :join? false}))

  (.stop server)

  )
