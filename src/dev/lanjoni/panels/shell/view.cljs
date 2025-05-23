(ns dev.lanjoni.panels.shell.view
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.panels.shell.components :refer [navbar footer]]
            [helix.dom :as d]
            [helix.core :refer [$]]))

(defnc app-shell
  [{:keys [content]}]
  (d/div
   {:className "container lg:mx-auto lg:max-w-screen-lg px-4 max-w-screen-sm flex flex-col min-h-screen"
    :data-testid "app-shell-container"}
   ($ navbar {})
   ($ content)
   ($ footer {})))
