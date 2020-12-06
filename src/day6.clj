(ns day6
  (:refer-clojure)
  (:require [common :refer [load-input]]))

(defn solve61 [input]
  (let [result-seq (remove #(= [""] %) (partition-by #(= % "") input))]
    (->> result-seq
         (map #(apply concat %))
         (map distinct)
         (map count)
         (apply +))))

;;1
(solve61 (load-input 6))

