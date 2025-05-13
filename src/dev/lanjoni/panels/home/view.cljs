(ns dev.lanjoni.panels.home.view
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.dom :as d]))

(defnc home [{:keys [_]}]
  (d/div
   (d/h1
    {:className "text-5xl font-black"}
    "guto lanjoni")
   (d/div
    {:className "text-xl mt-6 flex flex-col space-y-6"}
    (d/p
     "software engineer, open source enthusiast, and passionate learner currently shaping the future of finance at "
     (d/a
      {:href "https://nubank.com.br/"
       :target "_blank"
       :className "underline hover:text-purple transition duration-300"}
      "nubank")
     ", leveraging clojure to create seamless, user-focused banking experiences at one of the world's leading - and most innovative - fintech companies.")
    (d/p "i really love mechanical keyboards, photography, functional programming and lisp.")
    (d/figure
     (d/img
      {:src "/images/pictures/chopi_blackbird_at_pink_trumpet_tree.jpeg"
       :alt "chopi blackbird at pink trumpet tree"
       :className "w-full h-auto border-2 border-gray"})
     (d/figcaption
      {:className "text-center text-md text-gray mt-2"}
      "chopi blackbird at pink trumpet tree")))))
