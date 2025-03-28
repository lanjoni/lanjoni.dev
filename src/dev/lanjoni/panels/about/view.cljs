(ns dev.lanjoni.panels.about.view
  (:require [dev.lanjoni.components.link :refer [link]]
            [dev.lanjoni.panels.about.components :refer [content-list content-topic]]
            [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]))

(def content
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
    :description "a simple and fast configuration template for nix-darwin"}])

(defnc about [{:keys [_]}]
  (d/div
   {:className "space-y-6"}
   (d/h1
    {:className "text-5xl font-black"}
    "about me")

   ($ content-topic
      {:title "links"
       :content (d/p
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
                          :title "email"}))})

   ($ content-topic
      {:title "who am i?"
       :content (d/p
                 {:className "text-xl space-x-3 break-words"}
                 "a passionate student who always seeks to share knowledge, learn a lot, listen and understand how the world works. currently trying to expand and contribute to open-source projects, participating in communities aimed at democratizing access to technology education.")})

   ($ content-topic
      {:title "education"
       :content (d/p
                 {:className "text-xl space-x-3 break-words"}
                 "computer technician and future bachelor of science in information systems at "
                 ($ link {:url "https://vtp.ifsp.edu.br/"
                          :title "ifsp"})
                 ". i really like my college and the people i met there.")})

   ($ content-topic
      {:title "experiences"
       :content (d/div
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
                       "nubank")))})

   ($ content-topic
      {:title "projects"
       :content (d/ul
                 {:className "list-disc list-inside"}
                 ($ content-list
                    {:content content}))})))
