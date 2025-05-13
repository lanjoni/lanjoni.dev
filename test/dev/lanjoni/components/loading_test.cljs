(ns dev.lanjoni.components.loading-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.components.loading :refer [loading]]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest loading-component-test
  (testing "loading component should render with correct classes based on center property"
    (async done
           (p/catch
            (p/let [loading-component-true (tl/wait-for
                                            #(-> (tl/render ($ loading {:center true}))
                                                 (.findByTestId "loading-component")))
                    loading-component-false (tl/wait-for
                                             #(-> (tl/render ($ loading {:center false}))
                                                  (.findByTestId "loading-component")))]

              (is (match? "loading loading-bars loading-lg fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2"
                          (-> loading-component-true
                              (aget "className"))))

              (is (match? "loading loading-bars loading-lg"
                          (-> loading-component-false
                              (aget "className"))))

              (done))
            (fn [err] (is (= nil err))
              (done))))))
