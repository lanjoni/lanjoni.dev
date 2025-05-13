(ns dev.lanjoni.components.loading
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.dom :as d]))

(defnc loading [{:keys [center]}]
  (if center
    (d/span
     {:className "loading loading-bars loading-lg fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2"
      :data-testid "loading-component"})
    (d/span
     {:className "loading loading-bars loading-lg"
      :data-testid "loading-component"})))
