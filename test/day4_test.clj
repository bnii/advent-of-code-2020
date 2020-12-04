(ns day4-test
  (:require [clojure.test :refer :all])
  (:require [day4 :refer :all]))

(def example-data
  ["ecl:gry pid:860033327 eyr:2020 hcl:#fffffd"
   "byr:1937 iyr:2017 cid:147 hgt:183cm"
   ""
   "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884"
   "hcl:#cfa07d byr:1929"
   ""
   "hcl:#ae17e1 iyr:2013"
   "eyr:2024"
   "ecl:brn pid:760753108 byr:1931"
   "hgt:179cm"
   ""
   "hcl:#cfa07d eyr:2025 pid:166559648"
   "iyr:2011 ecl:brn hgt:59in"])

(deftest pairs-to-value-test
  (is (= (pairs-to-value ["asd:pds" "ddd:fff"]) {:asd "pds" :ddd "fff"})))


(deftest input->maps-test
  (is (= (input->maps (take 5 example-data)) [{:byr "1937"
                                               :cid "147"
                                               :ecl "gry"
                                               :eyr "2020"
                                               :hcl "#fffffd"
                                               :hgt "183cm"
                                               :iyr "2017"
                                               :pid "860033327"}
                                              {:byr "1929"
                                               :cid "350"
                                               :ecl "amb"
                                               :eyr "2023"
                                               :hcl "#cfa07d"
                                               :iyr "2013"
                                               :pid "028048884"}])))

