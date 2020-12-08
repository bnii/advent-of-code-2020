(ns day8
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]))

(defn parse-input [input]
  (let [splitted (map #(s/split % #" ") input)]
    (->> splitted
         (map (fn [[instr val]] [(keyword instr) (Long/parseLong val)]))
         vec)))

(defn first-repeating-instr [instrs]
  (loop [pointer 0 acc 0 visited #{}]
    (if (visited pointer)
      acc
      (let [[instr param] (instrs pointer)
            next-visited (conj visited pointer)]
        (case instr
          :acc (recur (inc pointer) (+ acc param) next-visited)
          :nop (recur (inc pointer) acc next-visited)
          :jmp (recur (+ param pointer) acc next-visited))))))

;; 1
(first-repeating-instr (parse-input (load-input 8)))