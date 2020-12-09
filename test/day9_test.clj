(ns day9-test
  (:require [clojure.test :refer :all])
  (:require [day9 :refer :all]
            [clojure.string :as s])
  (:import (clojure.lang PersistentQueue)))

(deftest advance-queue-test
  (is (=
        (advance-queue (apply conj (PersistentQueue/EMPTY) [1 2 3 4]) 5)
        [2 3 4 5])))

(def example-data
  (map #(Integer/parseInt %) (s/split-lines "35\n20\n15\n25\n47\n40\n62\n55\n65\n95\n102\n117\n150\n182\n127\n219\n299\n277\n309\n576")))

(deftest test-validity-test
  (is (= (test-validity 5 example-data)
         127)))

(deftest find-targetsum-seq-test
  (is (= (find-targetsum-seq 127 example-data)
         [15 25 47 40])))
