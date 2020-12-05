(ns day5-test
  (:require [clojure.test :refer :all])
  (:require [day5 :refer :all]))


(deftest getrow-test
  (is (= (getrow "FBFBBFFRLR") 44)))

(deftest getseat-test
  (is (= (getseat "FBFBBFFRLR") 5)))

(def example-data
  ["BFFFBBFRRR"
   "FFFBBBFRRR"
   "BBFFBBFRLL"])

(deftest max-seatid-test
  (is (= (max-seatid example-data) 820)))