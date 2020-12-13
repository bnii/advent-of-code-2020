(ns day13
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]))

(def example-data "939\n7,13,x,x,59,x,31,19")

(defn parse-input [input]
  (let [[f s] input]
    {:earliest-departure (Long/parseLong #p f)
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
(solve-1 (parse-input (load-input 13)))



