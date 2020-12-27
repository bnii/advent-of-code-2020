(ns day24-test
  (:require [clojure.test :refer :all])
  (:require [day24 :refer :all]))




(deftest parse-row-test
  (is (= (parse-row "e") [:e]))
  (is (= (parse-row "nw") [:nw]))
  (is (= (parse-row "seswneswswsenwwnwse") [:se :sw :ne :sw :sw :se :nw :w :nw :se])))


(deftest navigate-test
  (is (= (navigate [0 0] :e) [1 0]))
  (is (= (navigate [0 1] :e) [1 1]))
  (is (= (navigate [0 0] :se) [1 1]))
  (is (= (navigate [0 1] :se) [0 2])))

(deftest navigate-way-test
  (is (=
        (navigate-way [:nw :w :sw :e :e])
        [0 0])))

(def example-data (clojure.string/split-lines "sesenwnenenewseeswwswswwnenewsewsw\nneeenesenwnwwswnenewnwwsewnenwseswesw\nseswneswswsenwwnwse\nnwnwneseeswswnenewneswwnewseswneseene\nswweswneswnenwsewnwneneseenw\neesenwseswswnenwswnwnwsewwnwsene\nsewnenenenesenwsewnenwwwse\nwenwwweseeeweswwwnwwe\nwsweesenenewnwwnwsenewsenwwsesesenwne\nneeswseenwwswnwswswnw\nnenwswwsewswnenenewsenwsenwnesesenew\nenewnwewneswsewnwswenweswnenwsenwsw\nsweneswneswneneenwnewenewwneswswnese\nswwesenesewenwneswnwwneseswwne\nenesenwswwswneneswsenwnewswseenwsese\nwnwnesenesenenwwnenwsewesewsesesew\nnenewswnwewswnenesenwnesewesw\neneswnwswnwsenenwnwnwwseeswneewsenese\nneswnwewnwnwseenwseesewsenwsweewe\nwseweeenwnesenwwwswnew"))

(deftest calc-blacks-test
  (is (= (count (calc-blacks example-data)) 10)))

