(ns advent-of-code-2020.day1-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code-2020.day1 :refer :all]))


(def example-seq [1721 979 366 299 675 1456])

(deftest multiply-2020-test
  (is (= (multiply-2020 example-seq) 514579)))

(deftest multiply-3-2020-test
  (is (= (multiply-3-2020 example-seq) 241861950)))
