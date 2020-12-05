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

;;1
(max-seatid (load-input 5))

;;2
(def possible-seats-set (set (range 0 1024)))
(def input-seats-set (set (->> (load-input 5)
                               (map #(+
                                       (* (getrow %) 8)
                                       (getseat %))))))
(def emptys (apply sorted-set (clojure.set/difference possible-seats-set input-seats-set)))
(first (remove #(or (emptys (dec %)) (emptys (inc %))) emptys))
