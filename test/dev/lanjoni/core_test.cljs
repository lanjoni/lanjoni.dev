(ns dev.lanjoni.core-test
  (:require [cljs.test :refer [deftest is use-fixtures]]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.dom :as d]))

(use-fixtures :each tl/sync-setup)

(deftest a-component-test
  (let [container (d/div "helix")
        container (tl/render container)
        div       (.getByText container "helix")]
    ;; debug with prettyDOM
    ;; (require '["@testing-library/dom" :refer [prettyDOM]])
    ;; (js/console.log (prettyDOM div))
    (is (= "helix" (.-textContent div)))))
