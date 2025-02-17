(ns dev.lanjoni.views.content.state
  (:require [dev.lanjoni.infra.http :as http]
            [refx.alpha :as refx]))

(def content-fetch
  (fn [content-name]
    (-> (http/request! {:path   (str "/md/" content-name ".md")
                        :method :get})
        (.then (fn [response]
                 (refx/dispatch [:set-content (-> response :body)])))
        (.catch (fn [error]
                  (js/console.error error)
                  (throw error))))))
