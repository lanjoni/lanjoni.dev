(ns dev.lanjoni.refresh
  "A place to add preloads for developer tools!"
  (:require [dev.lanjoni.core :as app]
            [dev.lanjoni.infra.routes.core :refer [init-routes!]]
            [helix.experimental.refresh :as r]))

(r/inject-hook!)
(init-routes!)

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn ^:dev/after-load refresh []
  (r/refresh!))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn ^:dev/after-load clear-cache-and-render! []
  (app/render))
