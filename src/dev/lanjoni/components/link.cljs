(ns dev.lanjoni.components.link
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.dom :as d]))

(defnc link
  [{:keys [url title]}]
  (d/a
   {:href url
    :target "_blank"
    :className "underline hover:text-[#666666] transition duration-300"
    :data-testid "link-component"}
   title))
