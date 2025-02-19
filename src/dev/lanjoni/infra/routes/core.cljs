(ns dev.lanjoni.infra.routes.core
  (:require [dev.lanjoni.infra.routes.state :as routes.state]
            [dev.lanjoni.routes :refer [routes]]
            [reitit.coercion.spec :as rss]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]))

(defn on-navigate [new-match]
  (when new-match
    (routes.state/navigated new-match)))

(defn init-routes! []
  (let [router (rf/router routes {:data {:coercion rss/coercion}})]
    (routes.state/routes-db assoc :router router)
    (rfe/start! router on-navigate {:use-fragment true})))
