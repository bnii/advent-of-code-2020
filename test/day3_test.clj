(ns day3-test
  (:require [clojure.test :refer :all])
  (:require [day3 :refer :all]))

(def example-data
  ["..##......."
   "#...#...#.."
   ".#....#..#."
   "..#.#...#.#"
   ".#...##..#."
   "..#.##....."
   ".#.#.#....#"
   ".#........#"
   "#.##...#..."
   "#...##....#"
   ".#..#...#.#"])

(deftest slope-seq-test
  (is (= (slope-seq example-data 1 3) [\. \# \. \# \# \. \# \# \# \#])))

(deftest count-trees-test
  (is (= (count-trees (slope-seq example-data 1 3)) 7)))


