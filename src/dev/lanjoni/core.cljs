(ns dev.lanjoni.core
  (:require
   ["react" :as react]
   ["react-dom/client" :as react-dom-client]
   [helix.core :refer [$ defnc]]
   [refx.alpha :as refx]
   [dev.lanjoni.events]
   [dev.lanjoni.subs]
   [dev.lanjoni.routes :as routes]
   [reitit.coercion.spec :as rss]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]))

(defn on-navigate [new-match]
  (when new-match
    (refx/dispatch [:navigated new-match])))

(def router
  (rf/router
   routes/routes
   {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (rfe/start!
   router
   on-navigate
   ; use # fragment on route
   ; to not use this on servers you need special rules
   ; to redirect 404 to index.html configuration
   ; check ./nginx folder for an working docker example
   {:use-fragment true}))

(defnc router-component [{:keys [_]}]
  (let [current-route (refx/use-sub [:current-route])]
    (when current-route
      (-> current-route :data :view $))))

(enable-console-print!)

(defonce root (react-dom-client/createRoot (js/document.getElementById "app")))

(defn render []
  (.render root ($ router-component {:router router})))

(defn ^:dev/after-load clear-cache-and-render! []
  (refx/clear-subscription-cache!)
  (render))

(defn ^:export init []
  (refx/dispatch-sync [:initialize-db])
  (init-routes!)
  (render))
