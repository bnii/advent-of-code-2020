(ns day14
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]))

(defn parse-input [input]
  (loop [actual-mask "???"
         [actual-line & next-lines] input
         result      []]
    (if actual-line
      (let [[_ instr value] (re-matches #"^(.*) = (.*)" actual-line)]
        (if (= instr "mask")
          (recur value next-lines result)
          (let [[_ location] (re-matches #"^.*\[(\d+)\].*$" instr)]
            (recur actual-mask next-lines (conj result [(Long/parseLong location) (Long/parseLong value) actual-mask])))))
      result)))

(comment
  (def example-data (s/split-lines "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\nmem[8] = 11\nmem[7] = 101\nmem[8] = 0"))
  (parse-input example-data))


(defn apply-value-mask [num mask]
  (-> (map
        (fn [mask-bit num-bit]
          (if (= mask-bit \X) num-bit mask-bit))
        (reverse mask)
        (concat (reverse (Long/toBinaryString num)) (repeat \0)))
      reverse
      (#(Long/parseLong (apply str %) 2))))

(comment
  (apply-value-mask 101 mask))


(defn apply-value-masks [parsed-input]
  (->> parsed-input
       (map
         (fn [[location value mask]]
           [location (apply-value-mask value mask)]))
       (into {})))

(defn solve-1 [input]
  (let [applied (apply-value-masks (parse-input input))]
    (apply + (vals applied))))

(comment
  (solve-1 example-data))

;;1
(solve-1 (load-input 14))

;;;;;;;;;;;;;;;;;;

(defn apply-location-mask [location mask]
  (map
    (fn [mask-bit location-bit]
      (if (= mask-bit \0)
        location-bit
        mask-bit))
    (reverse mask)
    (concat (reverse (Long/toBinaryString location)) (repeat \0))))

(comment
  (apply-location-mask 42 "000000000000000000000000000000X1001X"))

(defn floating->concretes [floating-location]
  (loop [current-locations [[]]
         [actual-bit & remainder-bits] floating-location]
    (cond
      (nil? actual-bit) current-locations
      (#{\0 \1} actual-bit) (recur (map #(conj % actual-bit) current-locations) remainder-bits)
      :else (recur (concat
                     (map #(conj % \1) current-locations)
                     (map #(conj % \0) current-locations))
                   remainder-bits))))


(comment
  (floating->concretes '(\X \1 \0 \1 \1 \X \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0)))

(defn location-str->long [locations]
  (->> locations
       (map reverse)
       (map #(apply str %))
       (map #(Long/parseLong % 2))))

(comment
  (location-str->long (floating->concretes '(\X \1 \0 \1 \1 \X \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0))))

(defn run-instructions [parsed-input]
  (->> parsed-input
       (mapcat
         (fn [[location value mask]]
           (->> (apply-location-mask location mask)
                (floating->concretes)
                (location-str->long)
                (map (fn [location] [location value])))))
       (into {})
       (vals)
       (apply +)))


(comment
  (def example-data-2 (parse-input (s/split-lines "mask = 000000000000000000000000000000X1001X\nmem[42] = 100\nmask = 00000000000000000000000000000000X0XX\nmem[26] = 1")))
  (run-instructions example-data-2))

;;2
(time (run-instructions (parse-input (load-input 14))))