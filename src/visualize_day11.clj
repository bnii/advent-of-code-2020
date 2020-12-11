(ns visualize-day11
  (:refer-clojure)
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [day11 :as d11]
            [common :as c]
            [clojure.string :as s]))

(defn setup []
  (q/frame-rate 400)
  (d11/parse-input (c/load-input 11)))

(defn draw-state [layout]
  (let [cell-width  (/ (q/width) (count (first layout)))
        cell-height (/ (q/height) (count layout))]
    (dorun (for [rownum (range (count layout))
                 colnum (range (count (first layout)))]
             (let [color (case (get-in layout [rownum colnum])
                           :empty 0
                           :floor 125
                           :occupied 255)]
               (q/stroke color)
               (q/stroke-weight 1)
               (q/fill color)
               (q/ellipse (* rownum cell-width) (* colnum cell-height) cell-width cell-height))))))

(q/defsketch visualize
             :settings #(q/smooth 2)
             :setup setup
             :draw draw-state
             :update #(d11/next-table-state-with % d11/occupied-direction-count 5)
             :size [646 400]
             :host "host"
             :middleware [m/fun-mode])
