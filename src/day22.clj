(ns day22
  (:refer-clojure)
  (:require [clojure.string :as s]
            [common :refer [load-input]])
  (:import (clojure.lang PersistentQueue)))

;; - mindig a nyero kerul eloszor, es utana
;; loopolunk amig valamelyik pakli ures
;;

(comment
  (def example-data (s/split-lines "Player 1:\n9\n2\n6\n3\n1\n\nPlayer 2:\n5\n8\n4\n7\n10"))
  (def infinite-data (s/split-lines "Player 1:\n43\n19\n\nPlayer 2:\n2\n29\n14\n")))

(defn parse-input [input]
  (->> input
       (split-with #(not= "" %))
       (map #(remove (fn [v] (= v "")) %))
       (map rest)
       (map #(map (fn [v] (Long/parseLong v)) %))
       (map #(into PersistentQueue/EMPTY %))))

(defmethod print-method PersistentQueue [q, w]
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


(def play-2
  (fn clean
    ([p1-queue p2-queue]
     (play-2 p1-queue p2-queue #{}))
    ([p1-queue p2-queue already-played-pairs]
     (let [p1-card  (peek p1-queue)
           p2-card  (peek p2-queue)
           dck-pair [p1-queue p2-queue]]
       (if (and p1-card p2-card)
         (if (already-played-pairs dck-pair)
           {:winner 0
            :decks  [p1-queue p2-queue]}
           (if (and
                 (>= (count (pop p1-queue)) p1-card)
                 (>= (count (pop p2-queue)) p2-card))
             (let [subgame-result (play-2
                                    (into PersistentQueue/EMPTY (take p1-card) (pop p1-queue))
                                    (into PersistentQueue/EMPTY (take p2-card) (pop p2-queue)))]
               (if (= (:winner subgame-result) 0)
                 (recur
                   (conj (pop p1-queue) p1-card p2-card)
                   (pop p2-queue)
                   (conj already-played-pairs dck-pair))
                 (recur
                   (pop p1-queue)
                   (conj (pop p2-queue) p2-card p1-card)
                   (conj already-played-pairs dck-pair))))
             (if (> p1-card p2-card)
               (recur
                 (conj (pop p1-queue) p1-card p2-card)
                 (pop p2-queue)
                 (conj already-played-pairs dck-pair))
               (recur
                 (pop p1-queue)
                 (conj (pop p2-queue) p2-card p1-card)
                 (conj already-played-pairs dck-pair)))))
         {:winner (if (not-empty p1-queue) 0 1)
          :decks  [p1-queue p2-queue]})))))

(comment
  (apply play-2 (parse-input (load-input 22))))

(defn solve-2 [parsed-input]
  (apply + (map-indexed
             (fn [idx val]
               (* (inc idx)
                  val))
             (reverse
               (let [result (apply play-2 parsed-input)]
                 (get (:decks result) (:winner result)))))))

(comment
  (solve-2 (parse-input example-data)))

;;2
(time (solve-2 (parse-input (load-input 22))))