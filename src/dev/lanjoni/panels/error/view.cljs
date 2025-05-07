(ns dev.lanjoni.panels.error.view
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [dev.lanjoni.components.link :refer [link]]
            [helix.core :refer [$]]
            [helix.dom :as d]))

(defnc not-found [{:keys [_]}]
  (d/div
   {:className "fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2"
    :data-testid "error-view"}
   (d/div
    {:className "flex justify-center items-center flex-col mb-6"}
    (d/h1
     {:className "text-5xl font-black"}
     "404"))
   (d/img
    {:src "/images/memes/laziness-they-have-no-idea.webp"})
   (d/div
    {:className "flex justify-center items-center flex-col"}
    (d/div
     "Image from "
     ($ link
        {:title "clojure's deadly sin"
         :url "https://clojure-goes-fast.com/blog/clojures-deadly-sin/"})))))
