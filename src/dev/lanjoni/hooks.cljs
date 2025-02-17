(ns dev.lanjoni.hooks
  (:require [dev.lanjoni.utils :as utils]
            [helix.hooks :as hooks]
            [refx.alpha :as refx]))

(defn fetch-dark-mode []
  (let [dark-mode (refx/use-sub [:dark-mode])]
    (hooks/use-effect
      []
      (let [stored-theme (utils/get-stored-theme)]
        (when (not= dark-mode stored-theme)
          (refx/dispatch-sync [:set-dark-mode stored-theme]))
        (utils/apply-theme stored-theme))
      #js [])))
