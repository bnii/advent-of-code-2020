(ns day15
  (:refer-clojure)
  (:require [clojure.string :as s]))

(def example-data "0,3,6")

(defn calc-start-state [input]
  (let [starting-numbers  (->> (clojure.string/split input #",")
                               (map #(Long/parseLong %)))
        just-spoken       (last starting-numbers)
        turn-no           (count starting-numbers)
        last-spoken-turns (into {} (map-indexed
                                     (fn [idx val] [val (inc idx)])
                                     starting-numbers))]
    {:last-spoken-turns    last-spoken-turns
     :just-spoken          just-spoken
     :previously-spoken-at nil
     :turn-no              turn-no}))

(comment
  (calc-start-state example-data))

(defn advance-state [{:keys [last-spoken-turns just-spoken previously-spoken-at turn-no]}]
  (let [speak-now (if previously-spoken-at
                    (- (last-spoken-turns just-spoken) previously-spoken-at)
                    0)]
    {:turn-no              (inc turn-no)
     :last-spoken-turns    (assoc
                             last-spoken-turns
                             speak-now
                             (inc turn-no))
     :previously-spoken-at (last-spoken-turns speak-now)
     :just-spoken          speak-now}))

(defn calc-nth [input n]
  (:just-spoken
    (first
      (drop-while
        #(< (:turn-no %) n)
        (iterate advance-state (calc-start-state input))))))

(comment
  (calc-nth example-data 2020)
  (calc-nth example-data 30000000))

(def my-input "8,13,1,0,18,9")

(calc-nth my-input 2020)
(calc-nth my-input 30000000)