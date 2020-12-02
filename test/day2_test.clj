(ns day2-test
  (:require [clojure.test :refer :all])
  (:require [day2 :refer :all]))

(def example-data ["1-3 a: abcde"
                   "1-3 b: cdefg"
                   "2-9 c: ccccccccc"])

(deftest extract-test
  (is (= (extract (first example-data))
         {:min  1
          :max  3
          :char \a
          :pass "abcde"})))