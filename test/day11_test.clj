(ns day11-test
  (:require [clojure.test :refer :all])
  (:require [day11 :refer :all]
            [clojure.string :as s]))

(comment
  (occupied-neighbour-count [[:empty :occupied] [:occupied :floor]] 1 1)
  (parse-input example-data)
  (advance-table (parse-input example-data))
  (advance-table (advance-table (parse-input example-data)))
  (def example-data (s/split-lines "L.LL.LL.LL\nLLLLLLL.LL\nL.L.L..L..\nLLLL.LL.LL\nL.LL.LL.LL\nL.LLLLL.LL\n..L.L.....\nLLLLLLLLLL\nL.LLLLLL.L\nL.LLLLL.LL"))
  (def ex-8occ (parse-input (s/split-lines ".......#.\n...#.....\n.#.......\n.........\n..#L....#\n....#....\n.........\n#........\n...#.....")))
  (occupied-direction-count ex-8occ 4 3))


(deftest parse-input-test
  (is (=
        (parse-input (s/split-lines "L.\n#."))
        [[:empty :floor] [:occupied :floor]])))

(deftest occupied-neighbour-count-test
  (is (=
        (occupied-neighbour-count [[:empty :occupied] [:occupied :occupied]] 0 0)
        3))
  (is
    (=
      (occupied-neighbour-count [[:empty :empty] [:occupied :occupied]] 0 0)
      2)))

