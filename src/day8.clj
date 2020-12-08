(ns day8
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]))

(defn parse-input [input]
  (let [splitted (map #(s/split % #" ") input)]
    (->> splitted
         (map (fn [[instr val]] [(keyword instr) (Long/parseLong val)]))
         vec)))

(defn run-instrs [instrs]
  (loop [pointer 0 acc 0 visited #{}]
    (cond
      (visited pointer) {:type :infinite :acc acc}
      (= pointer (count instrs)) {:type :regular :acc acc}
      :else (let [[instr param] (instrs pointer)
                  next-visited (conj visited pointer)]
              (case instr
                :acc (recur (inc pointer) (+ acc param) next-visited)
                :nop (recur (inc pointer) acc next-visited)
                :jmp (recur (+ param pointer) acc next-visited))))))

;; 1
(run-instrs (parse-input (load-input 8)))

(defn modify-instrs [idx instrs]
  (let [[old-key value] (get instrs idx)]
    (case old-key
      :nop (assoc instrs idx [:jmp value])
      :jmp (assoc instrs idx [:nop value])
      nil)))

(defn find-regular-acc [instrs]
  (let [modified-instrs (keep-indexed modify-instrs (repeat (count instrs) instrs))
        regular-entry   (->> modified-instrs
                             (map run-instrs)
                             (filter #(= (:type %) :regular))
                             first)]
    (:acc regular-entry)))

;; 2
(find-regular-acc (parse-input (load-input 8)))
