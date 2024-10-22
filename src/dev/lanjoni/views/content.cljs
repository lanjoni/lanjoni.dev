(ns dev.lanjoni.views.content
  (:require
   [dev.lanjoni.panels.landing :refer [landing]]
   [dev.lanjoni.components.markdown :refer [markdown]]
   [helix.core :refer [$ defnc]]
   [helix.dom :as d]
   [helix.hooks :as hh]
   [kitchen-async.promise :as p]
   [lambdaisland.fetch :as fetch]
   [refx.alpha :as refx]))

(defnc content [{:keys [_]}]
  (let [current-route (refx/use-sub [:current-route])
        content-name (get-in current-route [:parameters :path :content-name])
        content (refx/use-sub [:content])]

    (hh/use-effect
      []
      (p/try
        (p/let [resp (fetch/get
                      (str "/md/" content-name ".md"))]
          (refx/dispatch [:set-content (:body resp)]))
        (p/catch :default e
          (prn :error e))))

    ($ landing
       {:content
        (d/article
         {:className "prose md:container md:mx-auto py-10"}
         ($ markdown {:content content}))})))
