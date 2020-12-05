(ns day5
  (:refer-clojure)
  (:require [common :refer [load-input]]))


(defn decode
  ([s] (Integer/parseInt (apply str s) 2)))

(defn getrow [t]
  (->> (subvec (vec t) 0 7)
       (map #(if (= \B %) 1 0))
       (apply str)
       (#(Integer/parseInt % 2))))

(defn getseat [t]
  (->> (subvec (vec t) 7 10)
       (map #(if (= \R %) 1 0))
       (apply str)
       (#(Integer/parseInt % 2))))

(defn max-seatid [inputs]
  (->> inputs
       (map #(+
               (* (getrow %) 8)
               (getseat %)))
       (apply max)))

(max-seatid (load-input 5))
