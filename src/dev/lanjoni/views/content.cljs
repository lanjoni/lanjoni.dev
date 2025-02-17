(ns dev.lanjoni.views.content
  (:require [dev.lanjoni.panels.landing :refer [landing]]
            [dev.lanjoni.components.markdown :refer [markdown]]
            [helix.core :refer [$ defnc]]
            [helix.dom :as d]
            [refx.alpha :as refx]))

(defnc content [{:keys [_]}]
  (let [content (refx/use-sub [:content])]
    ($ landing
       {:content
        (d/article
         {:className "prose md:container md:mx-auto py-10"}
         ($ markdown {:content content}))})))
