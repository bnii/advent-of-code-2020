(ns day9
  (:refer-clojure)
  (:require [common :refer [load-input-int]]
            [clojure.string :as s])
  (:import (clojure.lang PersistentQueue)))

(defn advance-queue [queue new-element]
  (pop (conj queue new-element)))

(defn test-validity [preamble-size input]
  (let [[preamble testables] (split-at preamble-size input)]
    (loop [[tested & next-testables] testables
           sliding-queue (apply conj PersistentQueue/EMPTY preamble)]
      (when tested
        (let [summed-set (set (for [num-1 sliding-queue
                                    num-2 sliding-queue
                                    :when (< num-1 num-2)]
                                (+' num-1 num-2)))]
          (if-not (summed-set tested)
            tested
            (recur
              next-testables
              (advance-queue sliding-queue tested))))))))

;;1
(test-validity 25 (load-input-int 9))

;;;;
(def target-sum (test-validity 25 (load-input-int 9)))

(defn find-targetsum-seq [target input]
  (loop [[n & r] input
         test-sequences []]
    (when n
      (let [candidate-seqs (->> test-sequences
                                (map #(conj % n))
                                (filter #(<= (apply + %) target)))]
        (if-let [found-seq (some
                             #(when (= (apply + %) target) %)
                             candidate-seqs)]
          found-seq
          (recur r (conj candidate-seqs [n])))))))

;;2
(let [good-seq (find-targetsum-seq target-sum (load-input-int 9))]
  (+ (apply max good-seq) (apply min good-seq)))
