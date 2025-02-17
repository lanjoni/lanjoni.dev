(ns dev.lanjoni.hooks
  (:require [dev.lanjoni.infra.flex.hook :refer [use-flex]]
            [town.lilac.flex :as flex]
            [town.lilac.flex.promise :as flex.promise]
            [dev.lanjoni.utils :as utils]
            [helix.hooks :as hooks]
            [refx.alpha :as refx]))

(def dark-mode-fetch
  (flex.promise/resource
   (fn [content-name]
     content-name)))

(def dark-mode-response
  (flex/signal {:state    @(:state dark-mode-fetch)
                :value    @(:value dark-mode-fetch)
                :error    @(:error dark-mode-fetch)
                :loading? @(:loading? dark-mode-fetch)}))

(defn fetch-dark-mode []
  (let [dark-mode (refx/use-sub [:dark-mode])]
    (hooks/use-effect
      []
      (let [stored-theme (utils/get-stored-theme)]
        (when (not= dark-mode stored-theme)
          (refx/dispatch-sync [:set-dark-mode stored-theme]))
        (utils/apply-theme stored-theme))
      #js [])))
