(ns dev.lanjoni.core-test
  (:require ["@testing-library/react" :as tlr]
            [cljs.test :refer [deftest is]]
            [helix.dom :as d]))

(deftest a-component-test
  (let [container (d/div "helix")
        container (tlr/render container)
        div       (.getByText container "helix")]
    (is (= "helix" (.-textContent div)))))
