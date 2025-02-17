(ns dev.lanjoni.components.content-topic
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.dom :as d]))

(defnc content-topic [{:keys [title content]}]
  (d/div
   {:className "text-xl flex flex-col space-y-2"}
   (d/h1
    {:className "text-3xl font-bold"}
    title)
   content))
