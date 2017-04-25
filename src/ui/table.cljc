(ns ui.table
  (:require [rum.core :as rum]))

(rum/defc TableRow [row]
          [:tr {}
           (map
             (fn [text]
               [:td {} text])
             row)])

(rum/defc TableHeader [row]
          [:thead {}
           (TableRow row)])

(rum/defc Table [movies]
          (let [header-row (->> movies first keys (map name))
                rows (map vals movies)]
            [:table {}
             (TableHeader header-row)
             [:tbody {}
              (map #(TableRow %)
                   rows)]]))
