(ns dev.lanjoni.panels.home.view-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.panels.home.view :refer [home]]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest home-view-test
  (async done
         (-> (p/let [rendered (tl/render ($ home))
                     heading (-> rendered (.getByText "guto lanjoni"))]
               (testing "home view should render heading with correct classes"
                 (is (match? "text-5xl font-black"
                             (-> heading
                                 (aget "className"))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ home))
                        nubank-link (-> rendered (.getByText "nubank"))]
                  (testing "home view should render nubank link correctly"
                    (is (= "https://nubank.com.br/" (-> nubank-link (aget "href"))))
                    (is (= "_blank" (-> nubank-link (aget "target"))))
                    (is (match? "underline hover:text-purple transition duration-300"
                                (-> nubank-link
                                    (aget "className"))))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ home))
                        image (-> rendered (.getByRole "img"))]
                  (testing "home view should render image correctly"
                    (is (-> image
                            (aget "src")
                            (.endsWith "/images/pictures/chopi_blackbird_at_pink_trumpet_tree.jpeg")))
                    (is (= "chopi blackbird at pink trumpet tree"
                           (-> image
                               (aget "alt"))))
                    (is (match? "w-full h-auto border-2 border-gray"
                                (-> image
                                    (aget "className"))))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ home))
                        caption (-> rendered (.getByText "chopi blackbird at pink trumpet tree"))]
                  (testing "home view should render image caption correctly"
                    (is (match? "text-center text-md text-gray mt-2"
                                (-> caption
                                    (aget "className"))))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ home))
                        text-content (-> rendered (.getByText #"i really love mechanical keyboards"))]
                  (testing "home view should render text content"
                    (is text-content)))))

             (p/then (fn [] (done)))
             (p/catch (fn [err]
                        (is (= nil err))
                        (done))))))
