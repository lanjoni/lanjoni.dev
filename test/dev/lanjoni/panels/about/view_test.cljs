(ns dev.lanjoni.panels.about.view-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.panels.about.view :refer [about]]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest about-view-test
  (async done
         (-> (p/let [rendered (tl/render ($ about))
                     sections (-> rendered (.getAllByRole "heading"))]
               (testing "about view should render all main sections"
                 (is (= ["about me" "links" "who am i?" "education" "experiences" "projects"]
                        (map (fn [s] (.-textContent s)) sections)))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ about))
                        links (-> rendered (.getAllByRole "link"))]
                  (testing "about view should render all social links correctly"
                    (is (some (fn [l] (and (= (.-href l) "https://github.com/lanjoni")
                                           (= (.-textContent l) "github"))) links))
                    (is (some (fn [l] (and (= (.-href l) "https://linkedin.com/in/lanjoni")
                                           (= (.-textContent l) "linkedin"))) links))
                    (is (some (fn [l] (and (= (.-href l) "https://x.com/gutolanjoni")
                                           (= (.-textContent l) "twitter"))) links))
                    (is (some (fn [l] (and (= (.-href l) "https://dev.to/guto")
                                           (= (.-textContent l) "dev"))) links))
                    (is (some (fn [l] (and (= (.-href l) "mailto:hey@lanjoni.com")
                                           (= (.-textContent l) "email"))) links))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ about))
                        project-links (-> rendered (.getAllByRole "listitem"))]
                  (testing "about view should render projects list correctly"
                    (is (= 7 (count project-links)))
                    (is (some (fn [p] (-> p .-textContent (.includes "crystal4noobs"))) project-links))
                    (is (some (fn [p] (-> p .-textContent (.includes "lpi4noobs"))) project-links))
                    (is (some (fn [p] (-> p .-textContent (.includes "clojure4noobs"))) project-links))
                    (is (some (fn [p] (-> p .-textContent (.includes "emerauth"))) project-links))
                    (is (some (fn [p] (-> p .-textContent (.includes "hackacrow"))) project-links))
                    (is (some (fn [p] (-> p .-textContent (.includes "datomic-getting-started"))) project-links))
                    (is (some (fn [p] (-> p .-textContent (.includes "snowflake"))) project-links))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ about))
                        nubank-link (-> rendered (.getByText "nubank"))
                        nimble-link (-> rendered (.getByText "nimble"))]
                  (testing "about view should render experiences section correctly"
                    (is (= "https://nubank.com.br/" (.-href nubank-link)))
                    (is (= "https://nimblevirtual.com.br/" (.-href nimble-link)))
                    (is (match? "underline font-bold hover:text-gray transition duration-300"
                                (.-className nubank-link)))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ about))
                        ifsp-link (-> rendered (.getByText "ifsp"))]
                  (testing "about view should render education section with IFSP link"
                    (is (= "https://vtp.ifsp.edu.br/" (.-href ifsp-link)))))))

             (p/then (fn [] (done)))
             (p/catch (fn [err]
                        (is (= nil err))
                        (done))))))
