(ns day17
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]
            [clojure.set :as set]))

(defn get-input-cells [input dimensions]
  (set (remove
         nil?
         (let [size-y (count input)
               size-x (count (first input))]
           (for [y (range size-y)
                 x (range size-x)]
             (when (= (get-in input [x y]) \#)
               (into [(- x (/ (dec size-x) 2))
                      (- y (/ (dec size-y) 2))]
                     (repeat (- dimensions 2) 0))))))))

(defn get-neigbours [cell]
  (disj
    (set (loop [dimension 0
                ret       [[]]]
           (if (= dimension (count cell))
             (map #(map + % cell) ret)
             (recur
               (inc dimension)
               (mapcat
                 #(map
                    (fn [v] (conj % v))
                    [-1 0 1])
                 ret)))))
    cell))


(defn candidate-live? [candidate-cell cells]
  (let [neighbour-count (count
                          (keep
                            cells
                            (get-neigbours candidate-cell)))]
    (or
      (and
        (cells candidate-cell) (<= 2 neighbour-count 3))
      (and
        (not (cells candidate-cell)) (= neighbour-count 3)))))


(defn next-round [cells]
  (loop [candidatate-cells  cells
         checked-candidates #{}
         live-cells         #{}]
    (let [candidate       (first candidatate-cells)
          rest-candidates (rest candidatate-cells)]
      (if-not candidate
        live-cells
        (let [next-candidates         (if (cells candidate)
                                        (into
                                          rest-candidates
                                          (set/difference
                                            (get-neigbours candidate)
                                            checked-candidates))
                                        rest-candidates)
              next-live-cells         (if (candidate-live? candidate cells)
                                        (conj live-cells candidate)
                                        live-cells)
              next-checked-candidates (conj checked-candidates candidate)]
          (recur next-candidates next-checked-candidates next-live-cells))))))


(comment
  (def example-data (s/split-lines ".#.\n..#\n###"))
  (def example-parsed (get-input-cells example-data 4))
  (candidate-live? [0 0 0 0] example-parsed)
  (next-round example-parsed)
  (get-neigbours [3 4])
  (count (nth (iterate next-round example-parsed) 6)))



;;1
(count (nth (iterate next-round (get-input-cells (load-input 17) 3)) 6))

;;2
(count (nth (iterate next-round (get-input-cells (load-input 17) 4)) 6))