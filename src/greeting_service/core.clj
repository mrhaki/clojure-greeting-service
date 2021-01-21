(ns greeting-service.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.util.response :refer [response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-response]]
            [compojure.core :refer [defroutes GET]])
  (:gen-class))

(def counter (atom 0))

(defn greeting-handler [name]
  (let [message (format "Hello, %s!" name)]
    (swap! counter inc)
    (response {:id @counter :content message})))

(defroutes api
  (GET "/greeting" {{:keys [name] :or {name "World"}} :params} (greeting-handler name)))

(def app (-> api
             wrap-json-response
             wrap-keyword-params
             wrap-params))

(defn start-server []
  (run-server app {:port 8080}))

(defn -main
  "Start server to serve /greeting endpoint"
  [& args]
  (start-server))


(comment
  ;; Stop server
  (server)

  ;; Start server
  (def server (start-server))

  )
