(ns dev.lanjoni.panels.shell.components-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.panels.shell.components :as components]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest navbar-items-component-test
  (testing "navbar items component should render with correct properties"
    (async done
           (p/catch
            (p/let [navbar-items-component (tl/wait-for
                                            #(-> (tl/render ($ components/navbar-items
                                                               {:tab-index 0
                                                                :class-properties "some classes here"}))
                                                 (.findByTestId "navbar-items-component")))]

              (is (match? "some classes here"
                          (-> navbar-items-component
                              (aget "className"))))

              (is (match? 0
                          (-> navbar-items-component
                              (aget "tabIndex"))))

              (done))
            (fn [err] (is (= nil err))
              (done))))))

(deftest theme-controller-component-test
  (testing "theme controller component should render with correct properties"
    (async done
           (p/catch
            (p/let [theme-controller-component (tl/render ($ components/theme-controller {}))
                    theme-controller-button    (tl/wait-for #(.findByTestId ^js/Object theme-controller-component "theme-controller-button"))
                    theme-controller-icon      (tl/wait-for #(.findByTestId ^js/Object theme-controller-component "theme-controller-icon"))]

              (is (match? "theme-controller hidden"
                          (-> theme-controller-button
                              (aget "className"))))

              (is (match? "swap-off h-10 w-10 fill-current transform transition duration-300 ease-in-out cursor-pointer"
                          (-> theme-controller-icon
                              (aget "className")
                              (aget "baseVal"))))

              (done))
            (fn [err] (is (= nil err))
              (done))))))

(deftest navbar-component-test
  (testing "navbar component should render with correct properties"
    (async done
           (p/catch
            (p/let [navbar-component (tl/wait-for
                                      #(-> (tl/render ($ components/navbar {}))
                                           (.findByTestId "navbar-component")))]

              (is (match? "navbar bg-base-100"
                          (-> navbar-component
                              (aget "className"))))

              (done))
            (fn [err] (is (= nil err))
              (done))))))

(deftest footer-component-test
  (testing "footer component should render with correct properties"
    (async done
           (p/catch
            (p/let [footer-component (tl/wait-for
                                      #(-> (tl/render ($ components/footer {}))
                                           (.findByTestId "footer-component")))]

              (is (match? "footer footer-center bg-base text-base-content p-4 mt-auto"
                          (-> footer-component
                              (aget "className"))))

              (done))
            (fn [err] (is (= nil err))
              (done))))))
