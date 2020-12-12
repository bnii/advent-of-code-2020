(ns day12
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]))

(set! *warn-on-reflection* true)

(defn parse-input [input]
  (map (fn [s]
         (let [[action-chars val-chars] (split-at 1 s)]
           {:action (keyword (str (first action-chars)))
            :val    (Integer/parseInt (apply str val-chars))}))
       input))

(def pos-diff
  {:E [1 0]
   :S [0 1]
   :W [-1 0]
   :N [0 -1]})

(def next-dirs
  {:R [:E :S :W :N]
   :L [:N :W :S :E]})

(defn step-1 [{:keys [direction position]} {:keys [action val]}]
  (cond
    (#{:E :W :N :S} action)
    {:direction direction
     :position  (map #(+ %1 (* val %2)) position (pos-diff action))}
    (#{:R :L} action)
    {:direction (let [turns      (/ val 90)
                      turn-steps (-> action
                                     (next-dirs)
                                     (cycle))]
                  (->> turn-steps
                       (drop-while #(not= % direction))
                       (drop turns)
                       (first)))
     :position  position}
    (#{:F} action)
    {:direction direction
     :position  (map #(+ %1 (* val %2)) position (pos-diff direction))}))

(defn calc-result [f moves]
  (let [{[xd yd] :position} (reduce f {:direction :E :position [0 0] :waypoint [10 -1]} moves)]
    (+ (Math/abs ^long xd) (Math/abs ^long yd))))

;;1
(calc-result step-1 (parse-input (load-input 12)))

(defn rotate-waypoint [direction [xc yc]]
  (case direction
    :R [(- yc) xc]
    :L [yc (- xc)]))

(defn step-2 [{:keys [position waypoint]} {:keys [action val]}]
  (cond
    (#{:E :W :N :S} action)
    {:position position
     :waypoint (map #(+ %1 (* val %2)) waypoint (pos-diff action))}
    (#{:R :L} action)
    {:position position
     :waypoint (nth
                 (iterate #(rotate-waypoint action %) waypoint)
                 (* (/ val 90)))}
    (#{:F} action)
    {:position (map #(+ %1 (* val %2)) position waypoint)
     :waypoint waypoint}))

;; 2
(calc-result step-2 (parse-input (load-input 12)))