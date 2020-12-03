(ns day3
  (:refer-clojure)
  (:require [common :refer [load-input]]))

(defn slope-seq [s down-step right-step]
  (loop [ns (map cycle s) slope []]
    (if-let [re (seq (drop down-step ns))]
      (let [m (map #(drop right-step %) re)]
        (recur m (conj slope (ffirst m))))
      slope)))

(defn count-trees [slopes]
  (count (filter #(= \# %) slopes)))

(count-trees (slope-seq (load-input 3) 1 3))

(let [terrain (load-input 3)
      steps   [[1 1] [1 3] [1 5] [1 7] [2 1]]]
  (->>
    steps
    (map #(apply slope-seq terrain %))
    (map #(filter (fn [c] (= \# c)) %))
    (map count)
    (apply *)))

