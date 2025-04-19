(ns dev.lanjoni.utils
  (:require [clojure.edn :as edn]))

(defn get-stored-preferences []
  (let [stored-preferences (js/localStorage.getItem "preferences")]
    (if stored-preferences
      (edn/read-string stored-preferences)
      {:dark-mode true})))

(defn set-stored-preferences [preferences]
  (js/localStorage.setItem "preferences" (pr-str preferences)))

(defn apply-theme [is-dark-mode]
  (let [theme (if is-dark-mode "black" "lofi")]
    (.setAttribute js/document.documentElement "data-theme" theme)))
