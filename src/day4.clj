(ns day4
  (:refer-clojure)
  (:require [common :refer [load-input]]))


(defn pairs-to-value [vs]
  (->>
    vs
    (map #(re-matches #"^(.*)\:(.*)$" %))
    (map rest)
    (map (fn [[k v]] [(keyword k) v]))
    (into {})))

(def required-attributes #{:ecl :byr :hcl :eyr :pid :iyr :hgt})

(defn input->maps [input]
  (let [passports-splitted (partition-by #(= "" %) input)
        removed-empty      (remove #(= [""] %) passports-splitted)]
    (->> removed-empty
         (map #(clojure.string/join " " %))
         (map #(clojure.string/split % #" "))
         (map pairs-to-value))))

(->> (load-input 4)
     input->maps
     (map keys)
     (map set)
     (map #(clojure.set/difference required-attributes %))
     (filter #(= #{} %))
     count)

