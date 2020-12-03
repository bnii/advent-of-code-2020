(ns day3
  (:refer-clojure)
  (:require [common :refer [load-input]]))

(defn slope-seq [s down-step right-step]
  (loop [ns (map cycle s) slope []]
    (sc.api/spy)
    (if-let [re (seq (drop down-step ns))]
      (let [m (map #(drop right-step %) re)]
        (recur m (conj slope (ffirst m))))
      slope)))

(defn count-trees [slopes]
  (count (filter #(= \# %) slopes)))

(count-trees (slope-seq (load-input 3) 1 3))
