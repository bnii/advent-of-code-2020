(ns day3
  (:refer-clojure)
  (:require [common :refer [load-input]]))

(defn slope-seq [s]
  (loop [ns (map cycle s) slope []]
    (if-let [re (next ns)]
      (let [m (map #(drop 3 %) re)]
        (recur m (conj slope (ffirst m))))
      slope)))

(defn count-trees [slopes]
  (count (filter #(= \# %) slopes)))

(count-trees (slope-seq (load-input 3)))