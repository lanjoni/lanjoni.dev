(ns dev.lanjoni.panels.about.components
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.dom :as d]))

(defnc content-list
  [{:keys [content]}]
  (for [{:keys [url title description]}
        content]
    (d/li
     {:key title
      :className "my-2"}
     (d/a
      {:href url
       :target "_blank"
       :className "underline hover:text-gray transition duration-300"}
      title)
     (d/span ":")
     (d/span
      {:className "ml-2"}
      description))))

(defnc content-topic [{:keys [title content]}]
  (d/div
   {:className "text-xl flex flex-col space-y-2"}
   (d/h1
    {:className "text-3xl font-bold"}
    title)
   content))
