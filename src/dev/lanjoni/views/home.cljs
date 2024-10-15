(ns dev.lanjoni.views.home
  (:require
   [dev.lanjoni.panels.landing :refer [landing]]
   [helix.core :refer [$ defnc]]
   [helix.dom :as d]))

(defnc home []
  ($ landing
     {:content
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
           :className "underline hover:text-[#820CD1] transition duration-300"}
          "nubank")
         ", leveraging clojure to create seamless, user-focused banking experiences at one of the world's leading - and most innovative - fintech companies.")
        (d/p "i really love mechanical keyboards, photography, functional programming and lisp.")))}))
