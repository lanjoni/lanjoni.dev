(ns dev.lanjoni.panels.navbar
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.components.navbar-items :refer [navbar-items]]
            [dev.lanjoni.components.theme-controller :refer [theme-controller]]
            [helix.core :refer [$]]
            [helix.dom :as d]))

(defnc navbar [{:keys [_]}]
  (d/div
   {:className "navbar bg-base-100"}
   (d/div
    {:className "navbar-start"}
    (d/div
     {:className "dropdown"}
     (d/div
      {:tabIndex 0
       :role "button"
       :className "btn btn-ghost lg:hidden"}
      (d/svg
       {:xmlns "http://www.w3.org/2000/svg"
        :className "h-5 w-5"
        :fill "none"
        :viewBox "0 0 24 24"
        :stroke "currentColor"}
       (d/path
        {:strokeLinecap "round"
         :strokeLinejoin "round"
         :strokeWidth 2
         :d "M4 6h16M4 12h8m-8 6h16"})))
     ($ navbar-items {:tab-index 0
                      :class-properties "menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow"}))
    #_(d/a
       {:className "btn btn-ghost text-xl"
        :href "/#/"}
       "guto"))
   (d/div
    {:className "navbar-center hidden lg:flex"}
    ($ navbar-items {:tab-index 0
                     :class-properties "menu menu-horizontal px-1 text-lg"}))
   (d/div
    {:className "navbar-end"}
    ($ theme-controller {}))))
