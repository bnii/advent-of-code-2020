(ns day10
  (:refer-clojure)
  (:require [common :refer [load-input-int]]))

(defn jolt-diffs [input]
  (let [sorted-adapters (sort input)
        source-jolts    (concat [0] sorted-adapters)
        target-jolts    (concat sorted-adapters [(+ 3 (last sorted-adapters))])]
    (frequencies (map - target-jolts source-jolts))))

;;1
(apply * (vals (jolt-diffs (load-input-int 10))))



