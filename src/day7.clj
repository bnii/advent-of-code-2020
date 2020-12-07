(ns day7
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [ubergraph.core :as uber]
            [ubergraph.alg :as alg]))

(defn row->edges
  "returning an sequence of edges of [contained-bag container-bag num-it-can-hold]"
  [row]
  (let [[_ container-bag contained-bags-string] (re-matches #"^(.*) bags? contain (.*)\.$" row)
        contained-bags-splitted (clojure.string/split contained-bags-string #", ")]
    (->> contained-bags-splitted
         (map #(re-matches #"^(\d+) ([a-z ]+) bags?" %))
         (map (fn [[_ occur bag]] [bag container-bag (Integer/parseInt occur)])))))


(defn get-graph [rows]
  (let [graph-init-directed-weighted (apply concat (map row->edges (remove #(re-matches #".*no other.*" %) rows)))]
    (apply uber/digraph graph-init-directed-weighted)))

(def bag-containment-graph (get-graph (load-input 7)))

;;1
(dec (count (alg/topsort bag-containment-graph "shiny gold")))

(def contained-count-by
  (memoize (fn [graph node]
             (let [predecs (uber/predecessors graph node)]
               (->> predecs
                    (map #(*
                            (uber/weight graph % node)
                            (inc (contained-count-by graph %))))
                    (apply +))))))

;;2
(contained-count-by bag-containment-graph "shiny gold")

