(ns day23
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]))

(comment
  "current cup + deck tartas? nem is kell current talan, eleg az order?
   lista: current cup + pickup + rest"
  (def example-data (map #(Long/parseLong (str %)) "389125467")))



(defn dest-cup [cups]
  (let [[current-cup & remaining-cups] cups
        [pick-up after-pick-up] (split-at 3 remaining-cups)
        remaining-cups (concat [current-cup] after-pick-up)
        dest-cup       (some (set remaining-cups) (concat (range (dec current-cup) 0 -1) [(apply max remaining-cups)]))
        [prev-dest _ post-dest] (partition-by #(not= % dest-cup) remaining-cups)
        concattd       (concat prev-dest [dest-cup] pick-up post-dest)]
    (concat (rest concattd) [(first concattd)])))

(comment
  (dest-cup example-data)
  (dest-cup '(2 8 9 1 5 4 6 7 3))
  (dest-cup '(2 5 8 3 6 7 4 1 9)))

(defn solve-1 [input]
  (apply
    str
    (rest (take 9 (drop-while
                    #(not= % 1)
                    (cycle
                      (first
                        (drop 100
                              (iterate
                                dest-cup
                                (map #(Long/parseLong (str %)) input))))))))))

;;1
(solve-1 "469217538")