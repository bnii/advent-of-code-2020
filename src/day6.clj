(ns day6
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.set :as set]))

(defn solve61 [input]
  (let [result-seq (remove #(= [""] %) (partition-by #(= % "") input))]
    (->> result-seq
         (map #(apply concat %))
         (map distinct)
         (map count)
         (apply +))))

;;1
(solve61 (load-input 6))

(defn solve62 [input]
      (let [result-seq (remove #(= [""] %) (partition-by #(= % "") input))]
        (->> result-seq
             (map #(map set %))
             (map #(apply set/intersection %))
             (map count)
             (apply +))))
;;2
(solve62 (load-input 6))
