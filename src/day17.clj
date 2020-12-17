(ns day17
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]
            [clojure.set :as set]
            [clojure.data :as data])) `


(defn parse-input [input]
  (set (remove
         nil?
         (let [size-y (count input)
               size-x (count (first input))]
           (for [y (range size-y)
                 x (range size-x)]
             (when (= (get-in input [x y]) \#)
               [(- x (/ (dec size-x) 2))
                (- y (/ (dec size-y) 2))
                0]))))))

(def example-data (s/split-lines ".#.\n..#\n###"))

(def example-parsed (parse-input example-data))

(defn get-neigbours [[x y z :as cell]]
  (set (for [xn [(dec x) x (inc x)]
             yn [(dec y) y (inc y)]
             zn [(dec z) z (inc z)]
             :when (not= [xn yn zn] cell)]
         [xn yn zn])))

(defn candidate-live? [candidate cubes]
  (let [neighbour-count (count
                          (keep
                            cubes
                            (get-neigbours candidate)))]
    (or
      (and
        (cubes candidate) (<= 2 neighbour-count 3))
      (and
        (not (cubes candidate)) (= neighbour-count 3)))))


(candidate-live? [0 0 0] example-parsed)

(defn next-round [state]
  (loop [candidates         state
         checked-candidates #{}
         live-cells         #{}]
    (let [candidate          (first candidates)
          rest-candidates    (rest candidates)]
      (if-not candidate
        live-cells
        (let [next-candidates         (if (state candidate)
                                        (into rest-candidates (set/difference
                                                                (get-neigbours candidate)
                                                                checked-candidates))
                                        rest-candidates)
              next-live-cells         (if (candidate-live? candidate state)
                                        (conj live-cells candidate)
                                        live-cells)
              next-checked-candidates (conj checked-candidates candidate)]
          (recur next-candidates next-checked-candidates next-live-cells))))))



(comment
  (data/diff
    example-parsed
    #{[0 -1 0] [1 0 0] [-1 1 0] [0 1 0] [1 1 0]}
    (parse-input (s/split-lines ".#.\n...\n...")))
  (next-round example-parsed)
  (get-neigbours [1 2 3])
  (count (nth (iterate next-round example-parsed) 6))
  (count (nth (iterate next-round (parse-input (load-input 17))) 6)))

