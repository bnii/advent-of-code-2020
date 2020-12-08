(ns day8-test
  (:require [clojure.test :refer :all])
  (:require [day8 :refer :all]
            [clojure.string :as s]))


(def example-data (vec (s/split-lines "nop +0\nacc +1\njmp +4\nacc +3\njmp -3\nacc -99\nacc +1\njmp -4\nacc +6")))
(def example-parsed [[:nop 0] [:acc 1] [:jmp 4] [:acc 3] [:jmp -3] [:acc -99] [:acc 1] [:jmp -4] [:acc 6]])

(deftest parse-input-test
  (is (= (parse-input example-data) example-parsed)))

(deftest run-instrs-test
  (is (=
        (run-instrs example-parsed)
        {:type :infinite :acc 5}))
  (is (=
        (run-instrs (assoc example-parsed 7 [:nop -4]))
        {:type :regular :acc 8})))

(deftest find-regular-acc-test
  (is (=
        (find-regular-acc example-parsed)
        8)))


