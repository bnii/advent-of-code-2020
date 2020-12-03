(ns day3-test
  (:require [clojure.test :refer :all])
  (:require [day3 :refer :all]))

(deftest parse-test)

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
  (is (= (slope-seq example-data) [\. \# \. \# \# \. \# \# \# \#])))

(deftest count-trees-test
  (is (= (count-trees (slope-seq example-data)) 7)))


