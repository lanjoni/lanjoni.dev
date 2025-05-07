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
  (async done
         (-> (p/let [content-topic-component (tl/wait-for
                                              #(-> (tl/render ($ components/content-topic {:title "some-title"
                                                                                           :content "some-content"}))
                                                   (.findByTestId "content-topic-component")))]
               (testing "content topic component should render with correct classes"
                 (is (match? "text-xl flex flex-col space-y-2"
                             (-> content-topic-component
                                 (aget "className"))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ components/content-topic
                                               {:title "Test Title"
                                                :content "Test Content"}))
                        title-element (-> rendered
                                          (.getByText "Test Title"))]
                  (testing "content topic component should render title correctly"
                    (is (match? "text-3xl font-bold"
                                (-> title-element
                                    (aget "className"))))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ components/content-topic
                                               {:title "Test Title"
                                                :content "Test Content"}))
                        content-text (-> rendered
                                         (.getByText "Test Content"))]
                  (testing "content topic component should render content correctly"
                    (is content-text)))))

             (p/then (fn [] (done)))
             (p/catch (fn [err]
                        (is (= nil err))
                        (done))))))

(deftest content-list-component-test
  (async done
         (-> (p/let [content [{:url "https://test1.com"
                               :title "Test 1"
                               :description "Description 1"}
                              {:url "https://test2.com"
                               :title "Test 2"
                               :description "Description 2"}]
                     rendered (tl/render ($ components/content-list {:content content}))
                     list-items (-> rendered
                                    (.getAllByTestId "content-list-component"))]
               (testing "content list should render multiple items correctly"
                 (is (= (count list-items) 2))))

             (p/then
              (fn []
                (p/let [content [{:url "https://test.com"
                                  :title "Test Link"
                                  :description "Test Description"}]
                        rendered (tl/render ($ components/content-list {:content content}))
                        link (-> rendered
                                 (.getByText "Test Link"))]
                  (testing "content list items should have correct link attributes"
                    (is (match? "https://test.com/"
                                (-> link
                                    (aget "href"))))
                    (is (match? "_blank"
                                (-> link
                                    (aget "target"))))
                    (is (match? "underline hover:text-gray transition duration-300"
                                (-> link
                                    (aget "className"))))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ components/content-list {:content []}))]
                  (testing "content list should handle empty content"
                    (is (not (-> rendered
                                 (.queryByTestId "content-list-component"))))))))

             (p/then
              (fn []
                (p/let [content [{:url "https://test.com"
                                  :title "Test Title"
                                  :description "Test Description"}]
                        rendered (tl/render ($ components/content-list {:content content}))
                        description (-> rendered
                                        (.getByText "Test Description"))]
                  (testing "content list items should render description correctly"
                    (is description)
                    (is (match? "ml-2"
                                (-> description
                                    (aget "className"))))))))

             (p/then (fn [] (done)))
             (p/catch (fn [err]
                        (is (= nil err))
                        (done))))))
