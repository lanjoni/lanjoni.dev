(ns dev.lanjoni.panels.landing
  (:require
   [dev.lanjoni.panels.navbar :refer [navbar]]
   [dev.lanjoni.panels.footer :refer [footer]]
   [helix.core :refer [$ defnc]]
   [helix.dom :as d]))

(defnc landing
  [{:keys [content]}]
  (d/div
   {:class "container lg:mx-auto lg:max-w-screen-lg px-4 max-w-screen-sm flex flex-col min-h-screen"}
   ($ navbar {})
   content
   ($ footer {})))
