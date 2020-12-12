(ns day12-test
  (:require [clojure.test :refer :all]
            [day12 :refer :all]
            [clojure.string :as s]))

(def example-data (s/split-lines "F10\nN3\nF7\nR90\nF11"))
(def example-data-parsed
  [{:action :F, :val 10}
   {:action :N, :val 3}
   {:action :F, :val 7}
   {:action :R, :val 90}
   {:action :F, :val 11}])

(deftest parse-input-test
  (is (= (parse-input example-data) example-data-parsed)))

(deftest rotate-waypoint-test
  (is (= (rotate-waypoint :R [1 2]) [-2 1])))

(deftest calc-result-test
  (is (= (calc-result step-1 example-data-parsed) 25))
  (is (= (calc-result step-2 example-data-parsed) 286)))