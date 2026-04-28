(ns dev.lanjoni.panels.content.state
  (:require [dev.lanjoni.infra.http :as http]
            [town.lilac.flex :as flex]))

(defonce current-content-name
  (flex/source nil))

(defonce content-state
  (flex/source :unresolved))

(defonce content-value
  (flex/source nil))

(defonce content-error
  (flex/source nil))

(defonce request-seq
  (atom 0))

(defn- valid-content-name? [content-name]
  (and (string? content-name)
       (seq content-name)))

(defn- next-loading-state [state]
  (if (contains? #{:ready :error :refreshing} state)
    :refreshing
    :pending))

(defn content-fetch [content-name]
  (when (valid-content-name? content-name)
    (let [request-id (swap! request-seq inc)]
      (flex/batch
        (current-content-name content-name)
        (content-state (next-loading-state @content-state))
        (content-error nil))

      (-> (http/request! {:path   (str "/md/" content-name ".md")
                          :method :get})
          (.then (fn [response]
                   (when (= request-id @request-seq)
                     (flex/batch
                       (content-value (:body response))
                       (content-state :ready)
                       (content-error nil)))))
          (.catch (fn [error]
                    (js/console.error error)
                    (when (= request-id @request-seq)
                      (flex/batch
                        (content-error error)
                        (content-state :error)))))))))

(def loading?
  (flex/signal
   (contains? #{:pending :refreshing} @content-state)))

(def content-response
  (flex/signal {:state    @content-state
                :value    @content-value
                :error    @content-error
                :loading? @loading?}))
