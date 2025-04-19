(ns dev.lanjoni.panels.content.view
  (:require [dev.lanjoni.components.loading :refer [loading]]
            [dev.lanjoni.components.markdown :refer [markdown]]
            [dev.lanjoni.infra.flex.hook :refer [use-flex]]
            [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.panels.content.state :refer [content-response]]
            [helix.core :refer [$]]
            [helix.dom :as d]))

(defnc content [{:keys [_]}]
  (let [{:keys [value loading?]} (use-flex content-response)]
    (if loading?
      ($ loading {:center true})
      (d/article
       {:className "prose md:container md:mx-auto py-10"}
       ($ markdown {:content value})))))
