(ns dev.lanjoni.panels.content.view-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.panels.content.view :refer [content]]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest content-view-test
  (async done
         (-> (p/let [rendered (tl/render ($ content))
                     article (-> rendered (.getByRole "article"))]
               (testing "content view should render article with correct classes"
                 (is (match? "prose md:container md:mx-auto py-10"
                             (-> article
                                 (aget "className"))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ content))
                        markdown-component (-> rendered (.getByTestId "markdown-component"))]
                  (testing "content view should render markdown component"
                    (is markdown-component)))))

             (p/then (fn [] (done)))
             (p/catch (fn [err]
                        (is (= nil err))
                        (done))))))
