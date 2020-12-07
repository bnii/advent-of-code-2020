(ns day7-test
  (:require [clojure.test :refer :all])
  (:require [day7 :refer [row->edge]]))

(def example-row "posh lavender bags contain 4 light teal bags, 4 wavy turquoise bags, 1 dim yellow bag.")

(deftest row->edge-test
  (is (= (row->edge example-row)
         [["light teal" "posh lavender" 4]
          ["wavy turquoise" "posh lavender" 4]
          ["dim yellow" "posh lavender" 1]])))
