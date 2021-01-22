(ns greeting-service.core-test
  (:require [clojure.test :refer :all]
            [greeting-service.core :refer :all]
            [clojure.data.json :as json]
            [ring.mock.request :as mock]))

(deftest test-greeting-handler
  
  (testing "/greeting should return Hello, World! message"
    (let [response (app (mock/request :get "/greeting"))
          body     (json/read-str (:body response))]
      (is (= 200 (:status response))
      (is (= "Hello, World!" (get-in body ["content"]))))))

  (testing "/greeting?name=test should return Hello, test! message"
    (let [response (app (mock/request :get "/greeting" {:name "test"}))
          body     (json/read-str (:body response))]
      (is (= 200 (:status response))
      (is (= "Hello, test!" (get-in body ["content"])))))))
