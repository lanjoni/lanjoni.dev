(ns dev.lanjoni.views.content
  (:require [dev.lanjoni.panels.landing :refer [landing]]
            [dev.lanjoni.components.loading :refer [loading]]
            [dev.lanjoni.components.markdown :refer [markdown]]
            [dev.lanjoni.infra.flex.hook :refer [use-flex]]
            [dev.lanjoni.views.content.state :refer [content-response]]
            [helix.core :refer [$ defnc]]
            [helix.dom :as d]))

(defnc content [{:keys [_]}]
  (let [{:keys [value loading?]} (use-flex content-response)]
    ($ landing
       {:content
        (if loading?
          ($ loading {:center true})
          (d/article
           {:className "prose md:container md:mx-auto py-10"}
           ($ markdown {:content value})))})))
