(ns day13
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]))

(def example-data "939\n7,13,x,x,59,x,31,19")
(def example-data-small "111\n17,x,13,19")

(defn parse-input [input]
  (let [[f s] input]
    {:earliest-departure (Long/parseLong f)
     :ids                (as-> s $
                               (s/split $ #",")
                               (remove #(= "x" %) $)
                               (map #(Long/parseLong %) $))}))

(def example-parsed (parse-input (s/split-lines example-data)))

(defn next-bus-id [{:keys [ids earliest-departure]}]
  (->> ids
       (map #(iterate (fn [[id time]] [id (+ time id)]) [% 0]))
       (map #(drop-while (fn [[_ time]] (> earliest-departure time)) %))
       (map first)
       (reduce
         (fn [[id1 time1]
              [id2 time2]]
           (if (< time1 time2)
             [id1 time1]
             [id2 time2])))))


(next-bus-id (parse-input (load-input 13)))
(next-bus-id example-parsed)

(defn solve-1 [{earliest-departure :earliest-departure :as parsed-input}]
  (let [[id departure] (next-bus-id parsed-input)]
    (* id (- departure earliest-departure))))

(solve-1 example-parsed)

;;1
(solve-1 (parse-input (load-input 13)))

(defn parse-input-to-congruence-equations [input]
  (let [s (s/split (second input) #",")]
    (->> s
         (map-indexed (fn [id remainder] [id remainder]))
         (keep (fn [[idx val]] (when (not= val "x") [(- idx) (Long/parseLong val)]))))))

;;;; I SPENT A COUPLE OF HOURS ON THE MINUS SIGN!
;; didn't realize that the congruence equations need to take into account the previous
;; reminders not the ones that look nice.

(defn linear-congruence-solver [parsed-input]
  (let [N (apply * (map second parsed-input))]
    (->> parsed-input
         (map (fn [[bi ni]]
                (let [Ni (/ N ni)
                      xi (.modInverse (biginteger Ni) (biginteger ni))]
                  (*' xi Ni bi))))
         (apply +')
         (#(mod % N)))))

(def example-pairs (parse-input-to-congruence-equations (s/split-lines example-data)))
(def example-pairs-small (parse-input-to-congruence-equations (s/split-lines example-data-small)))

(linear-congruence-solver example-pairs)
(linear-congruence-solver example-pairs-small)

;;2
(linear-congruence-solver (parse-input-to-congruence-equations (load-input 13)))



