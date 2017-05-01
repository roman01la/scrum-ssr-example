(ns client.controllers.post)

(def initial-state
  {:loading? false
   :error nil})

(defmulti control (fn [action] action))

(defmethod control :init [_ [state] _]
  (if (nil? state)
    {:state initial-state}
    {:state state}))

(defmethod control :load [_ [{:keys [id]}] state]
  {:state {:loading? true}
   :rpc {:url "/api"
         :method "stories.by-id"
         :params {:id id}
         :on-success :load-ready
         :on-error :load-fail}})

(defmethod control :load-ready [_ [state]]
  {:state state})

(defmethod control :load-fail [_ [error] state]
  {:state (assoc state :error error
                       :loading? false)})
