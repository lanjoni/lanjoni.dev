(ns dev.lanjoni.hooks
  (:require
   [dev.lanjoni.utils :as utils]
   [helix.hooks :as hh]
   [refx.alpha :as refx]))

(defn fetch-dark-mode []
  (let [dark-mode (refx/use-sub [:dark-mode])]
    (hh/use-effect
      []
      (let [stored-theme (utils/get-stored-theme)]
        (when (not= dark-mode stored-theme)
          (refx/dispatch-sync [:set-dark-mode stored-theme]))
        (utils/apply-theme stored-theme))
      #js [])))
