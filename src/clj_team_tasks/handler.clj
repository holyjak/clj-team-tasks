(ns clj-team-tasks.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [net.cgrand.enlive-html :as html]
            [ring.util.response :refer [redirect]]))

(def state (atom {
                  :teams {}}))

(defn rand-id [id-set]
  (let [next-ids (repeatedly #(rand-int Integer/MAX_VALUE))]
    (first (remove id-set next-ids))))


(defn make-team [teams]
  (let [id (rand-id (set (keys teams)))]
    (assoc teams id {})))

(html/deftemplate index-page "templates/index.html" [])
;;------------------------------------------------------------------------------------------------

(defroutes app-routes
  (GET "/" [] (reduce str (index-page)))
  (GET "/register" [] (str (swap! state #(update-in % [:teams] make-team)))) ;; TODO How to get the added ID so that we can redirect to it?!
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
