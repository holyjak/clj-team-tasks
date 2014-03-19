(defproject clj-team-tasks "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
;;                 [hiccup "1.0.5"]    ;; req. by the stacktrace middleware; 1.0.5 fails with ring serve
                 ]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler clj-team-tasks.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]
                        [ring/ring-jetty-adapter "1.2.2"]
                        [ring/ring-devel "1.2.2"]
                        [org.clojure/tools.namespace "0.2.4"] ;; needed for reloading
                        ]}})
