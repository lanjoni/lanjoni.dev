(ns dev.lanjoni.components.link-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.components.link :refer [link]]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest link-component-test
  (testing "link component should render with correct classes, href url and title"
    (async done
           (p/catch
            (p/let [link-component (tl/wait-for
                                    #(-> (tl/render ($ link {:url "https://docs.clj.codes" :title "clj codes"}))
                                         (.findByTestId "link-component")))]

              (is (match? "<a href=\"https://docs.clj.codes\" target=\"_blank\" class=\"underline hover:text-gray transition duration-300\" data-testid=\"link-component\">clj codes</a>"
                          (-> link-component
                              (aget "outerHTML"))))

              (done))
            (fn [err] (is (= nil err))
              (done))))))
