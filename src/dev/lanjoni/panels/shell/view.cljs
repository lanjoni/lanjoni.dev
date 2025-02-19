(ns dev.lanjoni.panels.shell.view
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.panels.shell.components :refer [navbar footer]]
            [helix.core :refer [$]]
            [helix.dom :as d]))

(defnc app-shell
  [{:keys [content]}]
  (d/div
   {:class "container lg:mx-auto lg:max-w-screen-lg px-4 max-w-screen-sm flex flex-col min-h-screen"}
   ($ navbar {})
   ($ content)
   ($ footer {})))
