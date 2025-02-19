(ns dev.lanjoni.infra.user-config.state
  (:require [dev.lanjoni.utils :as utils]
            [town.lilac.flex :as flex]))

(def config
  (flex/source {:dark-mode (utils/get-stored-theme)}))

(def dark-mode-signal
  (flex/signal (:dark-mode @config)))

(flex/listen dark-mode-signal
             #(utils/set-stored-theme %))
