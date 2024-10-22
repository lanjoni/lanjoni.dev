(ns dev.lanjoni.events
  (:require
   [refx.alpha :as refx]
   [reitit.frontend.controllers :as rfc]
   [reitit.frontend.easy :as rfe]))

(refx/reg-fx
 :push-state
 (fn [route]
   (apply rfe/push-state route)))

(refx/reg-event-db
 :initialize-db
 (fn [db _]
   (if db
     db
     {:current-route nil})))

(refx/reg-event-fx
 :push-state
 (fn [_ [_ & route]]
   {:push-state route}))

(refx/reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match   (:current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (assoc new-match :controllers controllers)))))

(refx/reg-event-db
 :set-session
 (fn [db [_ session]]
   (assoc db :session session)))

(refx/reg-event-db
 :set-dark-mode
 (fn [db [_ dark-mode]]
   (assoc db :dark-mode dark-mode)))

(refx/reg-event-db
 :set-content
 (fn [db [_ content]]
   (assoc db :content content)))
