(ns day10
  (:refer-clojure)
  (:require [common :refer [load-input-int]]
            [clojure.math.combinatorics :as combo]
            [clojure.string :as s]))

(defn jolt-diffs [input]
  (let [sorted-adapters (sort input)
        source-jolts    (concat [0] sorted-adapters)
        target-jolts    (concat sorted-adapters [(+ 3 (last sorted-adapters))])]
    (frequencies (map - target-jolts source-jolts))))

;;1
(apply * (vals (jolt-diffs (load-input-int 10))))

(def ways-to-reach
  (memoize (fn [target adapter-set]
             (cond
               (= target 0) 1
               (adapter-set target) (->> (range 1 4)
                                         (map #(ways-to-reach (- target %) adapter-set))
                                         (apply +))
               :else 0))))

;;2
(time (ways-to-reach (apply max (load-input-int 10)) (set (load-input-int 10))))

(comment
  "Some failed attempts before. TODO: understand the complexity"
  (defn count-arrangements [input]
    (let [sorted-input   (sort input)
          all-candidates (combo/subsets sorted-input)
          diffs          (for [candidate all-candidates]
                           (let [source-jolts (concat [0] candidate)
                                 target-jolts (concat candidate [(+ 3 (last sorted-input))])]
                             (map -' target-jolts source-jolts)))
          viable         (filter #(<= (apply max %) 3) diffs)]
      (count viable)))
  "This didn't even finish for example set 2"
  (count-arrangements example-data-2)

  "This version finished for example set 2 (~0.3s) , but not for the real input:"
  (defn arrangements [input]
    (let [adapter-set (set input)
          target-jolt (apply max input)]
      (loop [current-paths #{[0]}
             results       #{}]
        (if (not-empty current-paths)
          (let [next-path       (first current-paths)
                remaining-paths (disj current-paths next-path)
                last-node       (last next-path)
                possible-nexts  (->> (range 1 4)
                                     (map #(+ last-node %))
                                     (keep adapter-set)
                                     (filter #(<= % target-jolt)))
                possible-paths  (map #(conj next-path %) possible-nexts)]
            (recur
              (into remaining-paths (filter #(< (last %) target-jolt) possible-paths))
              (into results (filter #(= (last %) target-jolt) possible-paths))))
          results))))
  (time (count (arrangements example-data-2))))



