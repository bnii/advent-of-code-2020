(ns day11
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]
            [clj-async-profiler.core :as prof]))

(defn parse-input [row]
  (mapv
    #(mapv (fn [c]
             (case c
               \# :occupied
               \. :floor
               \L :empty)) %) row))

(defn occupied-neighbour-count [floor-map row col]
  (let [possible-indices (for [row-change [-1 0 1]
                               col-change [-1 0 1]
                               :when (not= row-change col-change 0)]
                           [(+ row-change row) (+ col-change col)])
        filtered-indices (filter
                           (fn [[row' col']] (and
                                               (< -1 row' (count floor-map))
                                               (< -1 col' (count (first floor-map)))))
                           possible-indices)]
    (count (filter #(= % :occupied) (map #(get-in floor-map %) filtered-indices)))))

(defn next-cell-state [current-val c enabled-neighbours]
  (cond
    (and (= current-val :empty) (= c 0))
    :occupied
    (and (= current-val :occupied) (>= c  enabled-neighbours))
    :empty
    :else
    current-val))

(defn next-table-state-with [floor-map f enabled-neighbours]
  (vec (for [row (range (count floor-map))]
         (vec (for [col (range (count (first floor-map)))]
                (next-cell-state
                  (get-in floor-map [row col])
                  (f floor-map row col)
                  enabled-neighbours))))))

(defn count-occupied [floor-map]
  (count
    (filter
      #(= % :occupied)
      (for [row floor-map
            val row]
        val))))

(defn find-fixpoint [floor-map f enabled-neighbours]
  (second (last (take-while
                  (fn [[f s]] (not= f s))
                  (partition 2 1
                             (map count-occupied
                                  (iterate
                                    #(next-table-state-with % f enabled-neighbours)
                                    floor-map)))))))

;;1
(time (find-fixpoint
        (parse-input (load-input 11))
        occupied-neighbour-count
        4))


(defn occupied-direction-count [floor-map row col]
  (let [directions (for [row-change [-1 0 1]
                         col-change [-1 0 1]
                         :when (not= row-change col-change 0)]
                     [row-change col-change])
        occupieds  (filter
                     (fn [[rowchange colchange]]
                       (loop [next-row (+ row rowchange)
                              next-col (+ col colchange)]
                         (if (and
                               (< -1 next-row (count floor-map))
                               (< -1 next-col (count (first floor-map))))
                           (case (get-in floor-map [next-row next-col])
                             :floor (recur (+ next-row rowchange) (+ next-col colchange))
                             :occupied true
                             :empty false)
                           false)))
                     directions)]
    (count occupieds)))

;; 2
(time (find-fixpoint
        (parse-input (load-input 11))
        occupied-direction-count 5))
