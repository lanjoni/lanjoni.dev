(ns dev.lanjoni.routes
  (:require [dev.lanjoni.views.about :refer [about]]
            [dev.lanjoni.views.content :refer [content]]
            [dev.lanjoni.views.content.state :as content.state]
            [dev.lanjoni.views.home :refer [home]]
            [dev.lanjoni.views.writing :refer [writing]]))

(def routes
  ["/"
   [""
    {:name      ::home
     :view      home
     :link-text "home"
     :controllers [{}]}]

   ["about"
    {:name      ::about
     :view      about
     :link-text "about"
     :controllers [{}]}]

   ["writing"
    {:name      ::writing
     :view      writing
     :link-text "writing"
     :controllers [{}]}]

   ["writing/:content-name"
    {:name      ::content
     :view      content
     :link-text "content"
     :controllers [{:parameters {:path [:content-name]}
                    :start (fn [& params]
                             (let [{:keys [content-name]} (-> params first :path)]
                               (content.state/content-fetch content-name)))}]}]])
