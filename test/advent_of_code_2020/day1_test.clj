(ns day1-test
  (:require [clojure.test :refer :all])
  (:require [day1 :refer :all]))


(def example-seq [1721 979 366 299 675 1456])

(deftest multiply-2020-test
  (is (= (multiply-2020 example-seq) 514579)))
