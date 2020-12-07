(ns day7-test
  (:require [clojure.test :refer :all])
  (:require [day7 :refer :all]))

(def example-row "posh lavender bags contain 4 light teal bags, 4 wavy turquoise bags, 1 dim yellow bag.")

(deftest row->edge-test
  (is (= (row->edges example-row)
         [["light teal" "posh lavender" 4]
          ["wavy turquoise" "posh lavender" 4]
          ["dim yellow" "posh lavender" 1]])))


(def example-data-2 "shiny gold bags contain 2 dark red bags.\ndark red bags contain 2 dark orange bags.\ndark orange bags contain 2 dark yellow bags.\ndark yellow bags contain 2 dark green bags.\ndark green bags contain 2 dark blue bags.\ndark blue bags contain 2 dark violet bags.\ndark violet bags contain no other bags.")

(deftest contained-count-by-test
  (let [graph (get-graph (clojure.string/split example-data-2 #"\n"))]
    (is (=
          (contained-count-by graph "shiny gold")
          126))))
