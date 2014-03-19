(ns clj-team-tasks.test.handler-test
  (:use clojure.test
        ring.mock.request
        clj-team-tasks.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      ;;(is (= (:body response) "Hello World"))
      ))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "resources"
    (let [response (app (request :get "/dummy.js"))]
      (is (= (:status response) 200)))))
