(ns dev.lanjoni.panels.error.view-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.panels.error.view :refer [not-found]]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest error-view-test
  (async done
         (-> (p/let [rendered (tl/render ($ not-found))
                     heading (-> rendered (.getByText "404"))]
               (testing "error view should render 404 heading with correct classes"
                 (is heading)
                 (is (match? "text-5xl font-black"
                             (-> heading
                                 (aget "className"))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ not-found))
                        link (-> rendered (.getByText "clojure's deadly sin"))]
                  (testing "error view should render link to Clojure's deadly sin"
                    (is link)
                    (is (= "https://clojure-goes-fast.com/blog/clojures-deadly-sin/"
                           (-> link
                               (aget "href"))))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ not-found))
                        container (-> rendered (.getByTestId "error-view"))]
                  (testing "error view should have correct container classes"
                    (is (match? "fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2"
                                (-> container
                                    (aget "className"))))))))

             (p/then (fn [] (done)))
             (p/catch (fn [err]
                        (is (= nil err))
                        (done))))))
