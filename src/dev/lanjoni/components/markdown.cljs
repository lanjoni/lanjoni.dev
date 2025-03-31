(ns dev.lanjoni.components.markdown
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            ["react-markdown" :default ReactMarkdown]
            ["remark-gfm" :default remarkGfm]))

(defnc markdown [{:keys [content]}]
  (d/div
   {:data-testid "markdown-component"}
   ($ ReactMarkdown
      {:children content
       :remarkPlugins (clj->js [remarkGfm])})))
