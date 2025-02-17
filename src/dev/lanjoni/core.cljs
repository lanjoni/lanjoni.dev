(ns dev.lanjoni.core
  (:require ["react-dom/client" :as react-dom-client]
            [dev.lanjoni.infra.flex.hook :refer [use-flex]]
            [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.infra.routes.core :refer [init-routes!]]
            [dev.lanjoni.infra.routes.state :refer [routes-db]]
            [helix.core :refer [$]]))

(defnc app [{:keys []}]
  (let [{:keys [current-route]} (use-flex routes-db)]
    (when current-route
      (-> current-route :data :view $))))

(defonce root
  (react-dom-client/createRoot (js/document.getElementById "app")))

(defn render []
  (.render root ($ app)))

(defn ^:export init []
  (init-routes!)
  (render))
