(ns day10-test
  (:require [clojure.test :refer :all]
            [day10 :refer :all]
            [clojure.string :as s]))

(def example-data
  (map
    #(Long/parseLong %)
    (s/split-lines "16\n10\n15\n5\n1\n11\n7\n19\n6\n12\n4")))

(def example-data-2
  (map
    #(Long/parseLong %)
    (s/split-lines "28\n33\n18\n42\n31\n14\n46\n20\n48\n47\n24\n23\n49\n45\n19\n38\n39\n11\n1\n32\n25\n35\n8\n17\n7\n9\n4\n2\n34\n10\n3")))

(deftest jolt-diffs-test
  (is (= (jolt-diffs example-data) {1 7,3 5}))
  (is (= (jolt-diffs example-data-2) {1 22,3 10})))
