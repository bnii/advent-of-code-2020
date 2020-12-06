(ns day6-test
  (:require [clojure.test :refer :all]
            [day6 :refer :all]
            [clojure.string :as str]))

(def example-data (map str/trim (str/split-lines "abc

                  a
                  b
                  c

                  ab
                  ac

                  a
                  a
                  a
                  a

                  b")))

(deftest solve61-test
  (is (= (solve61 example-data) 11)))

(deftest solve62-test
  (is (= (solve62 example-data) 6)))


