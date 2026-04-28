(ns dev.lanjoni.routes
  (:require [dev.lanjoni.panels.about.view :as about.view]
            [dev.lanjoni.panels.content.view :as content.view]
            [dev.lanjoni.panels.home.view :as home.view]
            [dev.lanjoni.panels.writing.view :as writing.view]))

(def routes
  ["/"
   [""
    {:name      ::home
     :view      home.view/home
     :link-text "home"
     :controllers [{}]}]

   ["about"
    {:name      ::about
     :view      about.view/about
     :link-text "about"
     :controllers [{}]}]

   ["writing"
    {:name      ::writing
     :view      writing.view/writing
     :link-text "writing"
     :controllers [{}]}]

   ["writing/:content-name"
    {:name      ::content
     :view      content.view/content
     :link-text "content"
     :controllers [{}]}]])
