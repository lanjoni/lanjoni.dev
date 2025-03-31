(ns dev.lanjoni.components.markdown-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [dev.lanjoni.components.markdown :refer [markdown]]
            [dev.lanjoni.aux.testing-library :as tl]
            [helix.core :refer [$]]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest markdown-component-test
  (testing "markdown component should render markdown syntax and convert syntax"
    (async done
           (p/catch
            (p/let [h1-markdown-component (tl/wait-for
                                           #(-> (tl/render ($ markdown {:content "# Jacaré"}))
                                                (.findByTestId "markdown-component")))
                    code-markdown-component (tl/wait-for
                                             #(-> (tl/render ($ markdown {:content "```clojure\n(defn hello []\n  (js/console.log \"Hello, World!\")\n)```"}))
                                                  (.findByTestId "markdown-component")))
                    url-markdown-component (tl/wait-for
                                            #(-> (tl/render ($ markdown {:content "[link](https://lanjoni.dev)"}))
                                                 (.findByTestId "markdown-component")))
                    quote-markdown-component (tl/wait-for
                                              #(-> (tl/render ($ markdown {:content "> quote"}))
                                                   (.findByTestId "markdown-component")))
                    list-markdown-component (tl/wait-for
                                             #(-> (tl/render ($ markdown {:content "- item"}))
                                                  (.findByTestId "markdown-component")))
                    table-markdown-component (tl/wait-for
                                              #(-> (tl/render ($ markdown {:content "| a | b |\n|---|---|\n| 1 | 2 |"}))
                                                   (.findByTestId "markdown-component")))]

              (is (match? "<h1>Jacaré</h1>"
                          (-> h1-markdown-component
                              (aget "innerHTML"))))

              (is (match? "<pre><code class=\"language-clojure\">(defn hello []\n  (js/console.log \"Hello, World!\")\n)```\n</code></pre>"
                          (-> code-markdown-component
                              (aget "innerHTML"))))

              (is (match? "<p><a href=\"https://lanjoni.dev\">link</a></p>"
                          (-> url-markdown-component
                              (aget "innerHTML"))))

              (is (match? "<blockquote>\n<p>quote</p>\n</blockquote>"
                          (-> quote-markdown-component
                              (aget "innerHTML"))))

              (is (match? "<ul>\n<li>item</li>\n</ul>"
                          (-> list-markdown-component
                              (aget "innerHTML"))))

              (is (match? "<table><thead><tr><th>a</th><th>b</th></tr></thead><tbody><tr><td>1</td><td>2</td></tr></tbody></table>"
                          (-> table-markdown-component
                              (aget "innerHTML"))))

              (done))
            (fn [err] (is (= nil err))
              (done))))))
