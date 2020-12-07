(ns day7
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [ubergraph.core :as uber]
            [ubergraph.alg :as alg]))

(defn row->edge
  "returning an sequence of edges of [contained-bag container-bag num-it-can-hold]"
  [row]
  (let [[_ container-bag contained-bags-string] (re-matches #"^(.*) bags? contain (.*)\.$" row)
        contained-bags-splitted (clojure.string/split contained-bags-string #", ")]
    (->> contained-bags-splitted
         (map #(re-matches #"^(\d+) ([a-z ]+) bags?" %))
         (map (fn [[_ occur bag]] [bag container-bag (Integer/parseInt occur)])))))

(row->edge example-row)

(def graph-init-directed-weighted (apply concat (map row->edge (remove #(re-matches #".*no other.*" %) (load-input 7)))))
(def bag-containment-graph (apply uber/digraph graph-init-directed-weighted))

;;1
(dec (count (alg/topsort bag-containment-graph "shiny gold")))

(def contained-count-by
  (memoize (fn [node]
             (let [predecs (uber/predecessors bag-containment-graph node)]
               (->> predecs
                    (map #(*
                            (uber/weight bag-containment-graph % node)
                            (inc (contained-count-by %))))
                    (apply +))))))

;;2
(contained-count-by "shiny gold")

