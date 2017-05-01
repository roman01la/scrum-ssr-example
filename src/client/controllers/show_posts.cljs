(ns client.controllers.show-posts)

(def initial-state
  {:from 0
   :to 0
   :total 0
   :items []
   :loading? false
   :error nil})

(defmulti control (fn [action] action))

(defmethod control :init [_ [state] _]
  (if (nil? state)
    {:state initial-state}
    {:state state}))

(defmethod control :next [_ _ state]
  {:state (update state :current inc)})

(defmethod control :prev [_ _ state]
  {:state (update state :current dec)})

(defmethod control :load [_ [{:keys [id]}] state]
  (let [id (or id 1)]
    {:state (assoc state :loading? true)
     :rpc {:url "/api"
           :method "stories.show"
           :params {:id id}
           :on-success :load-ready
           :on-error :load-fail}}))

(defmethod control :load-ready [_ [state]]
  {:state state})

(defmethod control :load-fail [_ [error] state]
  {:state (assoc state :error error
                       :loading? false)})
