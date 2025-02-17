(ns dev.lanjoni.views.content.state
  (:require [dev.lanjoni.infra.http :as http]
            [dev.lanjoni.adapters.url :refer [safe-href->url-encoded]]
            [town.lilac.flex :as flex]
            [town.lilac.flex.promise :as flex.promise]))

(def content-fetch
  (flex.promise/resource
   (fn [content-name]
     (-> (http/request! {:path   (safe-href->url-encoded (str "/md/" content-name ".md"))
                         :method :get})
         (.then (fn [response]
                  (-> response :body)))
         (.catch (fn [error]
                   (js/console.error error)
                   (throw error)))))))

(def content-response
  (flex/signal {:state    @(:state content-fetch)
                :value    @(:value content-fetch)
                :error    @(:error content-fetch)
                :loading? @(:loading? content-fetch)}))
