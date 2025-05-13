(ns dev.lanjoni.panels.shell.components
  (:require [dev.lanjoni.infra.flex.hook :refer [use-flex]]
            [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.infra.user-config.state :as user-config.state]
            [dev.lanjoni.utils :as utils]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            [reitit.frontend.easy :as rfe]))

(defnc navbar-items
  [{:keys [class-properties tab-index]}]
  (d/ul
   {:tabIndex tab-index
    :className class-properties
    :data-testid "navbar-items-component"}
   (d/li
    (d/a
     {:onClick #(rfe/push-state :dev.lanjoni.routes/home)}
     "home"))
   (d/li
    (d/a
     {:onClick #(rfe/push-state :dev.lanjoni.routes/about)}
     "about"))
   (d/li
    (d/a
     {:onClick #(rfe/push-state :dev.lanjoni.routes/writing)}
     "writing"))))

(defnc theme-controller [{:keys [_]}]
  (let [{:keys [dark-mode]} (use-flex user-config.state/preferences-signal)]
    ;; fetch dark mode for the first time
    (hooks/use-effect [dark-mode] (utils/apply-theme dark-mode))

    (letfn [(toggle-theme []
              (let [new-dark-mode (not dark-mode)]
                (user-config.state/config #(assoc-in % [:preferences :dark-mode] new-dark-mode))))]

      (d/label
       {:data-testid "theme-controller-component"}
       (d/input
        {:type "checkbox"
         :className "theme-controller hidden"
         :onClick #(toggle-theme)
         :data-testid "theme-controller-button"})

       (if dark-mode
         (d/svg
          {:className "swap-off h-10 w-10 fill-current transform transition duration-300 ease-in-out cursor-pointer"
           :xmlns "http://www.w3.org/2000/svg"
           :viewBox "0 0 24 24"
           :data-testid "theme-controller-icon"}
          (d/path
           {:d "M5.64,17l-.71.71a1,1,0,0,0,0,1.41,1,1,0,0,0,1.41,0l.71-.71A1,1,0,0,0,5.64,17ZM5,12a1,1,0,0,0-1-1H3a1,1,0,0,0,0,2H4A1,1,0,0,0,5,12Zm7-7a1,1,0,0,0,1-1V3a1,1,0,0,0-2,0V4A1,1,0,0,0,12,5ZM5.64,7.05a1,1,0,0,0,.7.29,1,1,0,0,0,.71-.29,1,1,0,0,0,0-1.41l-.71-.71A1,1,0,0,0,4.93,6.34Zm12,.29a1,1,0,0,0,.7-.29l.71-.71a1,1,0,1,0-1.41-1.41L17,5.64a1,1,0,0,0,0,1.41A1,1,0,0,0,17.66,7.34ZM21,11H20a1,1,0,0,0,0,2h1a1,1,0,0,0,0-2Zm-9,8a1,1,0,0,0-1,1v1a1,1,0,0,0,2,0V20A1,1,0,0,0,12,19ZM18.36,17A1,1,0,0,0,17,18.36l.71.71a1,1,0,0,0,1.41,0,1,1,0,0,0,0-1.41ZM12,6.5A5.5,5.5,0,1,0,17.5,12,5.51,5.51,0,0,0,12,6.5Zm0,9A3.5,3.5,0,1,1,15.5,12,3.5,3.5,0,0,1,12,15.5Z"}))

         (d/svg
          {:className "swap-on h-10 w-10 fill-current transform transition duration-300 ease-in-out cursor-pointer"
           :xmlns "http://www.w3.org/2000/svg"
           :viewBox "0 0 24 24"
           :data-testid "theme-controller-icon"}
          (d/path
           {:d "M21.64,13a1,1,0,0,0-1.05-.14,8.05,8.05,0,0,1-3.37.73A8.15,8.15,0,0,1,9.08,5.49a8.59,8.59,0,0,1,.25-2A1,1,0,0,0,8,2.36,10.14,10.14,0,1,0,22,14.05,1,1,0,0,0,21.64,13Zm-9.5,6.69A8.14,8.14,0,0,1,7.08,5.22v.27A10.15,10.15,0,0,0,17.22,15.63a9.79,9.79,0,0,0,2.1-.22A8.11,8.11,0,0,1,12.14,19.73Z"})))))))

(defnc navbar [{:keys [_]}]
  (d/div
   {:className "navbar bg-base-100"
    :data-testid "navbar-component"}
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
                      :class-properties "menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow"})))
   (d/div
    {:className "navbar-center hidden lg:flex"}
    ($ navbar-items {:tab-index 0
                     :class-properties "menu menu-horizontal px-1 text-lg"}))
   (d/div
    {:className "navbar-end"}
    ($ theme-controller {}))))

(defnc footer
  [{:keys [_]}]
  (let [current-year (.getFullYear (js/Date.))]
    (d/footer
     {:className "footer footer-center bg-base text-base-content p-4 mt-auto"
      :data-testid "footer-component"}
     (d/aside
      (d/p "copyleft " current-year " - none right reserved by guto lanjoni")))))
