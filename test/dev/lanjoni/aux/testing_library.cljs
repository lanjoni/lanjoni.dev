(ns dev.lanjoni.aux.testing-library
  (:require ["@testing-library/react" :as tlr]))

(def wait-for tlr/waitFor)

(defn document []
  (tlr/getQueriesForElement (.-body js/document) tlr/queries))

(defn click
  [^js/Element el]
  (.click tlr/fireEvent el))

(defn change
  [^js/Element el value]
  (.change tlr/fireEvent el (clj->js {:target {:value value}})))

(defn testing-container []
  (let [div (js/document.createElement "div")]
    (.setAttribute div "data-testid" "tlr-test-root")
    (js/document.body.appendChild div)))

(defn render
  [component]
  (tlr/render component
              #js {:container (testing-container)}))

(defn cleanup
  ([] (tlr/cleanup))
  ([after-fn] (tlr/cleanup)
              (after-fn)))

#_{:clj-kondo/ignore [:unresolved-var]}
(defn async-cleanup []
  (cleanup))

#_{:clj-kondo/ignore [:unresolved-var]}
(defn sync-setup [f]
  (f)
  (cleanup))
