(ns dev.lanjoni.components.link
  (:require
   [helix.core :refer [defnc]]
   [helix.dom :as d]))

(defnc link
  [{:keys [url title]}]
  (d/a
   {:href url
    :target "_blank"
    :className "underline hover:text-[#666666] transition duration-300"}
   title))
