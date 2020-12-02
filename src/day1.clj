(ns day1
  (:refer-clojure)
  (:require [common :refer [load-input-int]]))

(defn multiply-2020 [s]
  (let [num-set         (set s)
        num-with-friend (first (filter #(num-set (- 2020 %)) s))]
    (* num-with-friend (- 2020 num-with-friend))))

(defn multiply-3-2020 [s]
  (apply * (first (let [num-set (set s)]
                    (for [n1 s
                          n2 s
                          :let [n3 (- 2020 n1 n2)]
                          :when (num-set n3)]
                      [n1 n2 n3])))))


(multiply-2020 (load-input-int 1))
(multiply-3-2020 (load-input-int 1))