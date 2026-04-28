(ns dev.lanjoni.panels.content.view
  (:require [dev.lanjoni.components.loading :refer [loading]]
            [dev.lanjoni.components.markdown :refer [markdown]]
            [dev.lanjoni.infra.flex.hook :refer [use-flex]]
            [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.panels.content.state :refer [content-fetch content-response]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc content [{:keys [current-route]}]
  (let [content-name (or (get-in current-route [:parameters :path :content-name])
                         (get-in current-route [:path-params :content-name]))
        {:keys [value loading?]} (use-flex content-response)]
    (hooks/use-effect [content-name]
      (when (seq content-name)
        (content-fetch content-name)))

    (if loading?
      ($ loading {:center true})
      (d/article
       {:className "prose md:container md:mx-auto py-10"}
       ($ markdown {:content value})))))
