(ns dev.lanjoni.panels.shell.view-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.panels.shell.view :as view]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest app-shell-test
  (async done
         (-> (p/let [rendered (tl/render ($ view/app-shell {:content (fn [] nil)}))
                     container (-> rendered
                                   (.getByTestId "app-shell-container"))]
               (testing "app shell should render container with correct classes"
                 (is (match? "container lg:mx-auto lg:max-w-screen-lg px-4 max-w-screen-sm flex flex-col min-h-screen"
                             (-> container
                                 (aget "className"))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ view/app-shell {:content (fn [] nil)}))
                        navbar (-> rendered
                                   (.getByTestId "navbar-component"))]
                  (testing "app shell should render navbar"
                    (is navbar)))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ view/app-shell {:content (fn [] nil)}))
                        footer (-> rendered
                                   (.getByTestId "footer-component"))]
                  (testing "app shell should render footer"
                    (is footer)))))

             (p/then (fn [] (done)))
             (p/catch (fn [err]
                        (is (= nil err))
                        (done))))))
