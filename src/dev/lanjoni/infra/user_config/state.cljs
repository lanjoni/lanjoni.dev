(ns dev.lanjoni.infra.user-config.state
  (:require [dev.lanjoni.utils :as utils]
            [town.lilac.flex :as flex]))

(def config
  (flex/source {:preferences (utils/get-stored-preferences)}))

(def preferences-signal
  (flex/signal (:preferences @config)))

(flex/listen preferences-signal
             #(utils/set-stored-preferences %))
