(ns dev.lanjoni.views.about
  (:require
   [dev.lanjoni.components.link :refer [link]]
   [dev.lanjoni.components.content-list :refer [content-list]]
   [dev.lanjoni.panels.landing :refer [landing]]
   [helix.core :refer [$ defnc]]
   [helix.dom :as d]))

(defnc about [{:keys [_]}]
  ($ landing
     {:content
      (d/div
       {:className "space-y-6"}
       (d/h1
        {:className "text-5xl font-black"}
        "about me")
       (d/div
        {:className "text-xl flex flex-col space-y-2"}
        (d/h1
         {:className "text-3xl font-bold"}
         "links")
        (d/p
         {:className "text-xl space-x-3 break-words"}
         ($ link {:url "https://github.com/lanjoni"
                  :title "github"})
         ($ link {:url "https://linkedin.com/in/lanjoni"
                  :title "linkedin"})
         ($ link {:url "https://x.com/gutolanjoni"
                  :title "twitter"})
         ($ link {:url "https://dev.to/guto"
                  :title "dev"})
         ($ link {:url "mailto:lanjoni@proton.me"
                  :title "email"})))

       (d/div
        {:className "text-xl flex flex-col space-y-2"}
        (d/h1
         {:className "text-3xl font-bold"}
         "who am i?")
        (d/p
         {:className "text-xl space-x-3 break-words"}
         "a passionate student who always seeks to share knowledge, learn a lot, listen and understand how the world works. currently trying to expand and contribute to open-source projects, participating in communities aimed at democratizing access to technology education."))

       (d/div
        {:className "text-xl flex flex-col space-y-2"}
        (d/h1
         {:className "text-3xl font-bold"}
         "education")
        (d/p
         {:className "text-xl space-x-3 break-words"}
         "computer technician and future bachelor of science in information systems at "
         (d/a {:href "https://vtp.ifsp.edu.br/"
               :target "_blank"
               :className "underline hover:text-[#820CD1] transition duration-300"}
              "ifsp")
         ". i really like my college and the people i met there."))

       (d/div
        {:className "text-xl flex flex-col space-y-2"}
        (d/h1
         {:className "text-3xl font-bold"}
         "experiences")
        (d/div
         {:className "space-y-1"}
         (d/p
          {:className "text-xl"}
          (d/del "software engineer at "
                 (d/a {:href "https://nimblevirtual.com.br/"
                       :target "_blank"
                       :className "underline font-bold hover:text-[#666666] transition duration-300"}
                      "nimble")))
         (d/p
          {:className "text-xl font-bold"}
          "software engineer at "
          (d/a {:href "https://nubank.com.br"
                :target "_blank"
                :className "underline font-bold hover:text-[#666666] transition duration-300"}
               "nubank"))))

       (d/div
        {:className "text-xl flex flex-col space-y-2"}
        (d/h1
         {:className "text-3xl font-bold"}
         "projects")
        (d/ul
         {:className "list-disc list-inside"}
         ($ content-list {:content
                          [{:url "https://github.com/lanjoni/crystal4noobs"
                            :title "crystal4noobs"
                            :description "a complete guide to crystal programming language which is responsible for my start inside the open-source community and the ambassadors program (so I'm the first - and unique - crystal ambassador from Brazil)"}
                           {:url "https://github.com/lanjoni/lpi4noobs"
                            :title "lpi4noobs"
                            :description "a complete guide to the Linux Professional Institute Essentials certification (in Portuguese)"}
                           {:url "https://github.com/lanjoni/clojure4noobs"
                            :title "clojure4noobs"
                            :description "a complete guide to Clojure programming language (in Portuguese)"}
                           {:url "https://github.com/emerauth/emerauth"
                            :title "emerauth"
                            :description "hands-free, configurable and extensible authentication & authorization"}
                           {:url "https://github.com/lanjoni/hackacrow"
                            :title "hackacrow"
                            :description "a test runner for any programming language (written in Crystal)"}
                           {:url "https://github.com/lanjoni/datomic-getting-started"
                            :title "datomic-getting-started"
                            :description "a getting started guide to Datomic"}
                           {:url "https://github.com/lanjoni/snowflake"
                            :title "snowflake"
                            :description "a simple and fast configuration template for nix-darwin"}]}))))}))
