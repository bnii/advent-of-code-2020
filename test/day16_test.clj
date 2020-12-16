(ns day16-test
  (:require [clojure.test :refer :all]
            [day16 :refer :all]
            [clojure.string :as s]))

(def example-data-parsed (parse-data (s/split-lines "class: 1-3 or 5-7\nrow: 6-11 or 33-44\nseat: 13-40 or 45-50\n\nyour ticket:\n7,1,14\n\nnearby tickets:\n7,3,47\n40,4,50\n55,2,20\n38,6,12")))
(def example-data-2-parsed (parse-data (s/split-lines "class: 0-1 or 4-19\nrow: 0-5 or 8-19\nseat: 0-13 or 16-19\n\nyour ticket:\n11,12,13\n\nnearby tickets:\n3,9,18\n15,1,5\n5,14,9")))

(deftest parse-fields-test
  (is (=
        (parse-fields ["class: 1-3 or 5-7"])
        [["class" [[1 3] [5 7]]]])))

(deftest filter-valid-tickets-test
  (is (=
        (filter-valid-tickets example-data-parsed)
        [[7 3 47]])))

(deftest value-valid?-test
  (is (true? (value-valid? [[1 10] [20 30]] 25)))
  (is (not (value-valid? [[1 10] [20 30]] 15))))

(deftest all-rules-test
  (is (=
        (all-rules (:fields example-data-parsed))
        '((1 3) (5 7) (6 11) (33 44) (13 40) (45 50)))))


(deftest valid-fields-for-ticket-test
  (is (=
        (valid-fields-for-ticket
          [["a"
            [[0 5]]]
           ["b"
            [[0 1] [4 16]]]]
          [5, 14, 9])
        '(("a" "b") ("b") ("b")))))

(deftest possible-fields-per-column-test
  (is (=
        (possible-fields-per-column
          (:fields example-data-2-parsed)
          (:nearby-tickets example-data-2-parsed))
        [#{"row"} #{"class" "row"} #{"class" "row" "seat"}])))

(deftest fixed-column-order-test
  (is (=
        (fixed-column-order example-data-2-parsed)
        ["row" "class" "seat"])))