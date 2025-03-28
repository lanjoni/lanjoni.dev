(ns dev.lanjoni.core
  (:require ["react-dom/client" :as rdom]
            [dev.lanjoni.infra.flex.hook :refer [use-flex]]
            [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.infra.routes.core :refer [init-routes!]]
            [dev.lanjoni.infra.routes.state :refer [routes-db]]
            [dev.lanjoni.panels.shell.view :refer [app-shell]]
            [dev.lanjoni.panels.error.view :refer [not-found]]
            [helix.core :refer [$]]))

(defnc app [{:keys []}]
  (let [{:keys [current-route]} (use-flex routes-db)]
    (if-let [view (-> current-route :data :view)]
      ($ app-shell {:content view})
      ($ not-found))))

(defonce root
  (rdom/createRoot (js/document.getElementById "app")))

(defn render []
  (.render root ($ app)))

(defn ^:export init []
  (init-routes!)
  (render))
