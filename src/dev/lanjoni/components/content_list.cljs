(ns dev.lanjoni.components.content-list
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
       :className "underline hover:text-[#666666] transition duration-300"}
      title)
     (d/span ":")
     (d/span
      {:className "ml-2"}
      description))))
