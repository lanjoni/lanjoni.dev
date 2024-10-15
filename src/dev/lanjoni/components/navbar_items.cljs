(ns dev.lanjoni.components.navbar-items
  (:require
   [helix.core :refer [defnc]]
   [helix.dom :as d]))

(defnc navbar-items
  [{:keys [class-properties tab-index]}]
  (d/ul
   {:tabIndex tab-index
    :className class-properties}
   (d/li
    (d/a
     {:href "/#/"}
     "Home"))
   (d/li
    (d/a
     {:href "/#/about"}
     "About"))
   (d/li
    (d/a
     {:href   "https://blog.lanjoni.dev"
      :target "_blank"}
     "Blog"))))
