(ns day1
  (:refer-clojure))

(defn multiply-2020 [s]
  (let [num-set         (set s)
        num-with-friend (first (filter #(num-set (- 2020 %)) s))]
    (* num-with-friend (- 2020 num-with-friend))))

(def my-input
  (map
    #(Integer/parseInt %)
    (clojure.string/split
      (slurp "src/advent_of_code_2020/puzzle1-input.txt")
      #"\n")))

(multiply-2020 my-input)