(ns dev.lanjoni.components.navbar-items
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.dom :as d]))

(defnc navbar-items
  [{:keys [class-properties tab-index]}]
  (d/ul
   {:tabIndex tab-index
    :className class-properties}
   (d/li
    (d/a
     {:href "/#/"}
     "home"))
   (d/li
    (d/a
     {:href "/#/about"}
     "about"))
   (d/li
    (d/a
     {:href   "/#/writing"}
     "writing"))))
