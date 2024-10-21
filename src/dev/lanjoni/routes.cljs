(ns dev.lanjoni.routes
  (:require
   [dev.lanjoni.views.about :refer [about]]
   [dev.lanjoni.views.home :refer [home]]
   [dev.lanjoni.views.writing :refer [writing]]
   [refx.alpha :as refx]))

(def routes
  ["/"
   [""
    {:name      ::home
     :view      home
     :link-text "Home"
     :controllers
     [{;; Do whatever initialization needed for home page
       ;; I.e (refx/dispatch [::events/load-something-with-ajax])
       ;; Teardown can be done here.
       }]}]

   ["about"
    {:name      ::about
     :view      about
     :link-text "about"
     :controllers
     [{;; Do whatever initialization needed for home page
       ;; I.e (refx/dispatch [::events/load-something-with-ajax])
       ;; Teardown can be done here.
       }]}]

   ["writing"
    {:name      ::writing
     :view      writing
     :link-text "writing"
     :controllers
     [{;; Do whatever initialization needed for home page
       ;; I.e (refx/dispatch [::events/load-something-with-ajax])
       ;; Teardown can be done here.
       }]}]

   ["not-found*"
    {:name ::not-found
     :view #(refx/dispatch [:push-state ::home])}]])
