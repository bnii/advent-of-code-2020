(ns day24
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.set :as set]))


(defn parse-row
  ([row]
   (parse-row (vec row) nil []))
  ([[f & r] pre-dir directions]
   (cond
     (nil? f) directions
     (and (= pre-dir \s) (= f \e)) (recur r nil (conj directions :se))
     (and (= pre-dir \n) (= f \e)) (recur r nil (conj directions :ne))
     (and (= pre-dir \s) (= f \w)) (recur r nil (conj directions :sw))
     (and (= pre-dir \n) (= f \w)) (recur r nil (conj directions :nw))
     (= f \e) (recur r nil (conj directions :e))
     (= f \w) (recur r nil (conj directions :w))
     (= f \s) (recur r \s directions)
     (= f \n) (recur r \n directions))))


(defn navigate [[x y] direction]
  (let [even+ (if (even? y) 1 0)]
    (case direction
      :e [(inc x) y]
      :se [(+ x even+) (inc y)]
      :sw [(+ x -1 even+) (inc y)]
      :w [(dec x) y]
      :nw [(+ x -1 even+) (dec y)]
      :ne [(+ x even+) (dec y)])))

(defn navigate-way [directions]
  (reduce
    navigate
    [0 0]
    directions))


(defn calc-blacks [input]
  (->> input
       (map parse-row)
       (map navigate-way)
       (reduce
         (fn [coord-set coord]
           (if (coord-set coord)
             (disj coord-set coord)
             (conj coord-set coord)))
         #{})))

(defn solve-1 [input]
  (count (calc-blacks input)))

;;1
(solve-1 (load-input 24))

(def black-tiles (calc-blacks (load-input 24)))

(defn get-neighbours [[x y]]
  (let [even+ (if (even? y) 1 0)]
    #{[(inc x) y]
      [(+ x even+) (inc y)]
      [(+ x -1 even+) (inc y)]
      [(dec x) y]
      [(+ x -1 even+) (dec y)]
      [(+ x even+) (dec y)]}))

(defn neighbour-num [black-set [x y]]
  (->> (get-neighbours [x y])
       (filter #(black-set %))
       count))


(defn next-state [black-set]
  (loop [[candidate & next-candidates]
         (apply set/union black-set (map get-neighbours black-set))
         next-black-set
         #{}]
    (cond
      (not candidate)
      next-black-set
      (and (black-set candidate) (<= 1 (neighbour-num black-set candidate) 2))
      (recur next-candidates (conj next-black-set candidate))
      (and (not (black-set candidate)) (= 2 (neighbour-num black-set candidate)))
      (recur next-candidates (conj next-black-set candidate))
      :else
      (recur next-candidates next-black-set))))


(defn solve-2 [black-set]
  (count (nth
           (iterate next-state black-set)
           100)))

;;2
(solve-2 black-tiles)