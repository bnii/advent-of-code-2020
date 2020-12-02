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

(deftest is-valid-pass-test
  (are
    [result n]
    (= result (if (is-valid-pass (extract (nth example-data n))) true false))
    true 0
    false 1
    true 2))

(deftest is-valid-pass-2-test
  (are
    [result n]
    (= result (if (is-valid-pass-2 (extract (nth example-data n))) true false))
    true 0
    false 1
    false 2))