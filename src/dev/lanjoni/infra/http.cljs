(ns dev.lanjoni.infra.http
  (:require [dev.lanjoni.infra.http.component :as http.component]))

(defn request! [request-input]
  (http.component/request (http.component/new-http)
                          request-input))
