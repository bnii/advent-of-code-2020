(ns day22
  (:refer-clojure)
  (:require [clojure.string :as s]
            [common :refer [load-input]])
  (:import (clojure.lang PersistentQueue)))

;; - mindig a nyero kerul eloszor, es utana
;; loopolunk amig valamelyik pakli ures
;;

(def example-data (s/split-lines "Player 1:\n9\n2\n6\n3\n1\n\nPlayer 2:\n5\n8\n4\n7\n10"))

(defn parse-input [input]
  (->> input
       (split-with #(not= "" %))
       (map #(remove (fn [v] (= v "")) %))
       (map rest)
       (map #(map (fn [v] (Long/parseLong v)) %))
       (map #(into PersistentQueue/EMPTY %))))

(defmethod print-method clojure.lang.PersistentQueue [q, w] ; Overload the printer for queues so they look like fish
  (print-method '<- w)
  (print-method (seq q) w)
  (print-method '-< w))

(comment
  (parse-input example-data))


(defn play [p1-queue p2-queue]
  (let [p1-card (peek p1-queue)
        p2-card (peek p2-queue)]
    (if (and p1-card p2-card)
      (if (> p1-card p2-card)
        (recur
          (conj (pop p1-queue) p1-card p2-card)
          (pop p2-queue))
        (recur
          (pop p1-queue)
          (conj (pop p2-queue) p2-card p1-card)))
      [p1-queue p2-queue])))


(defn solve-1 [parsed-input]
  (apply + (map-indexed
             (fn [idx val]
               (* (inc idx)
                  val))
             (reverse (first (filter not-empty (apply play parsed-input)))))))

(comment
  (solve-1 (parse-input example-data)))
;;1
(solve-1 (parse-input (load-input 22)))
