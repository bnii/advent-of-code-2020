(ns common
  (:refer-clojure))

(defn load-input [day]
  (clojure.string/split
    (slurp (str "src/day" day "-input.txt"))
    #"\n"))

(defn load-input-int [day]
  (map
    #(Long/parseLong %)
    (load-input day)))
