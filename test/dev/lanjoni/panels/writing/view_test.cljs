(ns dev.lanjoni.panels.writing.view-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.panels.writing.view :as view]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest writing-view-test
  (async done
         (-> (p/let [rendered (tl/render ($ view/writing {}))
                     title (-> rendered
                               (.getByText "writing"))]
               (testing "writing view should render title correctly"
                 (is (match? "text-5xl font-black"
                             (-> title
                                 (aget "className"))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ view/writing {}))
                        description (-> rendered
                                        (.getByText #"I write about software engineering"))]
                  (testing "writing view should render description"
                    (is description)
                    (is (match? "text-xl mt-6 flex flex-col space-y-6"
                                (-> description
                                    (aget "parentElement" "className"))))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ view/writing {}))
                        posts (-> rendered
                                  (.getAllByRole "heading" #js {:level 2}))]
                  (testing "writing view should render posts correctly"
                    (is (= 2 (count posts)))
                    (doseq [post posts]
                      (is (match? "text-3xl font-bold"
                                  (-> post
                                      (aget "className"))))
                      (is (match? "cursor-pointer hover:text-gray transition duration-300 ease-in-out"
                                  (-> post
                                      (aget "firstChild" "className")))))))))

             (p/then
              (fn []
                (p/let [rendered (tl/render ($ view/writing {}))
                        tag-containers (-> rendered
                                           (.getAllByTestId "tag-container"))]
                  (testing "writing view should render tags correctly"
                    (is (= 2 (count tag-containers)))
                    (doseq [container tag-containers]
                      (is (match? "space-x-2"
                                  (-> container
                                      (aget "className"))))
                      (let [tags (-> container
                                     (.getElementsByTagName "span"))]
                        (doseq [tag tags]
                          (is (match? "text-sm text-gray bg-[#F3F4F6] px-2 py-1 rounded"
                                      (-> tag
                                          (aget "className")))))))))))

             (p/then (fn [] (done)))
             (p/catch (fn [err]
                        (is (= nil err))
                        (done))))))
