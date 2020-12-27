(ns common
  (:refer-clojure)
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn slurp-day [day]
  (slurp (io/resource (str "day" day "-input.txt"))))

(defn load-input [day]
  (s/split-lines (slurp-day day)))

(defn load-input-int [day]
  (map
    #(Long/parseLong %)
    (load-input day)))
