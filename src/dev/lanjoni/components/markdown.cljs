(ns dev.lanjoni.components.markdown
  (:require [dev.lanjoni.infra.helix :refer [defnc]]
            [helix.core :refer [$]]
            ["react-markdown" :default ReactMarkdown]
            ["remark-gfm" :default remarkGfm]))

(defnc markdown [{:keys [content]}]
  ($ ReactMarkdown
     {:children content
      :remarkPlugins (clj->js [remarkGfm])}))
