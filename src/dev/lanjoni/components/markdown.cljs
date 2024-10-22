(ns dev.lanjoni.components.markdown
  (:require
   [helix.core :refer [$ defnc]]
   ["react-markdown" :default ReactMarkdown]
   ["remark-gfm" :default remarkGfm]))

(defnc markdown [{:keys [content]}]
  ($ ReactMarkdown
     {:children content
      :remarkPlugins (clj->js [remarkGfm])}))
