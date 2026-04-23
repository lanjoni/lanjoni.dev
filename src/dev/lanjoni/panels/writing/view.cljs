(ns dev.lanjoni.panels.writing.view
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.dom :as d]
            [reitit.frontend.easy :as rfe]))

(def ^:private content-list
  {:posts
   [{:title "It's okay to miss someone"
     :description "Feelings are part of us as humans, so it's okay to miss someone. (This post is unrelated to software engineering.)"
     :published-at "2026-04-23"
     :path "its-okay-to-miss-someone"
     :tags ["feelings" "saudade" "humans"]}

    {:title "Building elegant web applications with ClojureScript, React, and UIx! (Clojure South 2025)"
     :description "Clojure South is back in 2025! Here are some useful links from my presentation."
     :published-at "2025-10-07"
     :path "clojure-south-2025"
     :tags ["clojurescript" "uix" "react"]}

    {:title "Meet Datomic: the immutable and functional database."
     :description "Datomic is a functional database that provides a new way to think about databases!"
     :published-at "2024-04-15"
     :path "meet-datomic"
     :tags ["clojure" "database" "functional" "programming"]}

    {:title "How to write a CRUD CLI using Elixir and ScyllaDB"
     :description "Learn how to create your first CRUD CLI with ScyllaDB and Elixir!"
     :published-at "2023-09-18"
     :path "elixir-scylladb"
     :tags ["elixir" "scylladb" "cli"]}]})

(defnc writing [{:keys [_]}]
  (d/div
   (d/h1
    {:className "text-5xl font-black"}
    "writing")
   (d/div
    {:className "text-xl mt-6 flex flex-col space-y-6"}
    (d/p
     "I write about software engineering, open source, and functional programming. Here are some of my latest posts:")
    (d/div
     {:className "space-y-6"}
     (for [{:keys [title description published-at path tags]} (:posts content-list)]
       (d/div
        {:key path}
        (d/h2
         {:className "text-3xl font-bold"}
         (d/a
          {:onClick #(rfe/push-state :dev.lanjoni.routes/content {:content-name path})
           :className "cursor-pointer hover:text-gray transition duration-300 ease-in-out"}
          title))
        (d/p
         {:className "text-xl"}
         description)
        (d/p
         {:className "text-sm text-gray"}
         (str "Published at " published-at))
        (d/div
         {:className "space-x-2"
          :data-testid "tag-container"}
         (for [tag tags]
           (d/span
            {:className "text-sm text-gray bg-[#F3F4F6] px-2 py-1 rounded"
             :key tag}
            tag)))))))))
