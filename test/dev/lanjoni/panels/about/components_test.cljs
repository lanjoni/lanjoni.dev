(ns dev.lanjoni.panels.about.components-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.panels.about.components :as components]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest content-topic-component-test
  (testing "content topic component should render with correct classes"
    (async done
           (p/catch
            (p/let [content-topic-component (tl/wait-for
                                             #(-> (tl/render ($ components/content-topic {:title "some-title"
                                                                                          :content "some-content"}))
                                                  (.findByTestId "content-topic-component")))]

              (is (match? "text-xl flex flex-col space-y-2"
                          (-> content-topic-component
                              (aget "className"))))

              (done))
            (fn [err] (is (= nil err))
              (done))))))
