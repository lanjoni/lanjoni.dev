(ns dev.lanjoni.panels.footer
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.dom :as d]))

(defnc footer
  [{:keys [_]}]
  (let [current-year (.getFullYear (js/Date.))]
    (d/footer
     {:className "footer footer-center bg-base text-base-content p-4 mt-auto"}
     (d/aside
      (d/p "copyleft " current-year " - none right reserved by guto lanjoni")))))
