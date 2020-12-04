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

(defn number-between [d l h n-str]
  (when-let [n (re-matches (re-pattern (str "[0-9]{" d "}")) n-str)]
    (<= l (Long/parseLong n) h)))

(defn height-validator [h-str]
  (when-let [[_ num-str mertek] (re-matches #"([0-9]{2,3})(cm|in)" h-str)]
    (let [n (Integer/parseInt num-str)]
      (cond
        (and (= mertek "in") (<= 59 n 76)) :good-inch
        (and (= mertek "cm") (<= 150 n 193)) :good-cm))))

(def validators
  {:byr (partial number-between 4 1920 2002)
   :iyr (partial number-between 4 2010 2020)
   :eyr (partial number-between 4 2020 2030)
   :hgt height-validator
   :hcl #(re-matches #"#[0-9a-f]{6}" %)
   :ecl #(re-matches #"amb|blu|brn|gry|grn|hzl|oth" %)
   :pid #(re-matches #"[0-9]{9}" %)
   :cid identity})

(defn validate [val]
  (every? (fn [[k v]] ((validators k) v)) val))

;; 1
(->> (load-input 4)
     input->maps
     (map keys)
     (map set)
     (map #(clojure.set/difference required-attributes %))
     (filter #(= #{} %))
     count)

;; 2
(let [input-maps   (input->maps (load-input 4))
      is-valid     (map validate input-maps)
      all-required (->> input-maps
                        (map keys)
                        (map set)
                        (map #(clojure.set/difference required-attributes %))
                        (map #(= #{} %)))]
  (->> (map #(and %1 %2) is-valid all-required)
       (filter identity)
       count))



