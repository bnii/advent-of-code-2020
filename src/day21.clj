(ns day21
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]
            [clojure.set :as set]))

(comment
  (def example-input (s/split-lines "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\ntrh fvjkl sbzzf mxmxvkd (contains dairy)\nsqjhc fvjkl (contains soy)\nsqjhc mxmxvkd sbzzf (contains fish)")))


(defn parse-line [line]
  (let [[ing-str alleg-str] (s/split line #"\(contains ")
        ing   (s/split ing-str #"\s")
        alleg (s/split (s/escape alleg-str {\, "" \) ""}) #"\s")]
    [(set ing) (set alleg)]))

(comment
  (parse-line "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)"))


(defn parse-input [input]
  (map parse-line input))

(comment
  (parse-input example-input))


(defn allergen->possible-containers [set-pairs]
  (->> set-pairs
       (mapcat
         (fn [[foods allergens]]
           (map
             (fn [allergen] [allergen foods])
             allergens)))
       (reduce
         (fn [acc [allergen foods]]
           (update
             acc
             allergen
             (fn [existing-foods]
               (if (nil? existing-foods)
                 foods
                 (set/intersection existing-foods foods)))))
         {})))

(comment
  (allergen->possible-containers (parse-input example-input)))


(defn allergen->determined-container [set-pairs]
  (loop [current-map          (allergen->possible-containers set-pairs)
         determined-allergens {}]
    (if-let [[allerg food]
             (first (keep
                      (fn [[allerg foods]]
                        (when (= (count foods) 1)
                          [allerg (first foods)]))
                      current-map))]
      (recur
        (into {} (map
                   (fn [[allerg foods]]
                     [allerg (disj foods food)])
                   current-map))
        (assoc determined-allergens allerg food))
      determined-allergens)))

(defn solve-1 [input]
  (let [pair-set         (parse-input input)
        determined-foods (set (vals (allergen->determined-container pair-set)))]
    (->> pair-set
         (map first)
         (apply concat)
         (remove determined-foods)
         count)))

(comment
  (solve-1 example-input))

;;1
(solve-1 (load-input 21))


(defn solve-2 [input]
  (->> input
       (parse-input)
       (allergen->determined-container)
       (sort-by (fn [[all _]] all))
       (vals)
       (s/join ",")))

;;2
(solve-2 (load-input 21))