(ns clj-team-tasks.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [net.cgrand.enlive-html :as html]
            [ring.util.response :refer [redirect]]))

(def state (atom {:teams {}}))

;;(def [old-teams new-teams] (swap-in! state [:teams] make-team))
;; -> (let [id (rand-id* teams)] (update-in @state [:teams] assoc id []) id)

(defn swap-in!
  "Combination of update-in and swap! returning the value at the path before and after.
   Ex.: (swap-in! (atom {:k 1}) [:k] + 2) ;; returns [1 3]"
  [atom path [f-make & m-args] [f-set & s-args]]
  (loop [root @atom]
    (let [old-val (get-in root path)
          change (apply f-make old-val m-args)
          new-val (apply f-set old-val change s-args)]
      (if (compare-and-set! atom root (assoc-in root path new-val))
        change
        (recur @atom)))))

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
  (GET "/register" [] (let
                        [id (swap-in! state [:teams] [(comp rand-id set keys)] [assoc {}])]
                        (redirect (str "/team/" id))))
  (GET "/team/:id" [id] (str "Hello, team #" id "!"))
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

  (>trace-ns 'clj-team-tasks.handler)

  (swap-in! state [:teams] [(comp rand-id set keys)] [assoc {}])


  )
