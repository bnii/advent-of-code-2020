(ns day19
  (:refer-clojure)
  (:require [clojure.string :as s]
            [clojure.math.combinatorics :as combo]
            [common :refer [load-input]]
            [instaparse.core :as insta]))

(def example-data (s/split-lines "0: 4 1 5\n1: 2 3 | 3 2\n2: 4 4 | 5 5\n3: 4 5 | 5 4\n4: \"a\"\n5: \"b\"\n\nababbb\nbababa\nabbbab\naaabbb\naaaabbb"))

(defn combine-results [rule-seqs]
  (map
    #(apply str %)
    (apply combo/cartesian-product
           rule-seqs)))

(def gen-matches
  (memoize (fn [rules n]
             (if (string? (rules n))
               (rules n)
               (mapcat
                 (fn [rule-terms]
                   (->> rule-terms
                        (map #(gen-matches rules %))
                        combine-results))
                 (rules n))))))

(comment
  (combine-results [["asd" "dsa"] ["a" "bb" "ccc"]])
  (gen-matches [[1 2] "a" "b"] 0)
  (gen-matches [[[1]] "a"] 0)
  (gen-matches [[[1 2]] "a" "b"] 0)
  (gen-matches [[[1 2] [3]] "a" "b" "c"] 0)
  (gen-matches [[[1 2] [3]] "a" "b" [[1 1 1] [1 2 1]]] 0))

(defn parse-rule-line [rule-line]
  (let [[rule-id rule-strs] (s/split rule-line #":")]
    rule-strs
    [(Long/parseLong rule-id) (if (s/includes? rule-strs "\"")
                                (s/trim (s/replace rule-strs "\"" ""))
                                (map
                                  #(keep (fn [v]
                                           (when (not= "" v)
                                                 (Long/parseLong v)))
                                         (s/split % #" "))
                                  (s/split rule-strs #"\|")))]))

(comment
  (parse-rule-line "1: 2 3 | 3 2")
  (parse-rule-line "1: \"w\""))

(defn get-rules [input]
  (->> (first (partition-by #(= "" %) input))
       (map parse-rule-line)
       (into {})))

(defn get-patterns [input]
  (nth
    (partition-by #(= "" %) input)
    2))

(comment
  (get-rules example-data)
  (count (filter
           #((set (gen-matches (get-rules example-data) 0)) %)
           (get-patterns example-data))))

(defn solve-1 [input]
  (let [rule-set (set (gen-matches (get-rules input) 0))]
    (count
      (filter
        #(rule-set %)
        (get-patterns input)))))

(comment
  (get-patterns example-data)
  (get-rules (load-input 19))
  (solve-1 (load-input 19))
  (get-patterns (load-input 19)))

;;1
(solve-1 example-data)

;;;;;;;;;;;;;;;;;;
;; couldn't make it work with the previous approach -- stack overflow with mapcat/concat

(comment
  "This is for solving 1st part with instaparse. "
  ":start 0 -- spent quite some time on overlooking the point of the starter rulee"
  (def parser (insta/parser
                (s/join "\n" (concat ["S : 0"] (first (partition-by #(= "" %) (load-input 19)))))))
  "Example matches"
  (let [rule-set (set (gen-matches (get-rules (load-input 19)) 0))]
    (take 10
          (filter
            rule-set
            (get-patterns (load-input 19))))))


(comment
  "Just for playing with instaparse"
  (def ex-parser (insta/parser "S = A B | C D\nA =\"a\"\nB = A | B \nC = \"c\"\nD = \"d\"\n"))
  (ex-parser "aaa"))                                        ;; instaparse returns vec for successful parse


(defn solve-2 [input]
  (let [[rules-str _ patterns]
        (partition-by #(= "" %) input)
        modified-parser
        (insta/parser
          (s/join
            "\n"
            (map                                            ;; replacing the rows 11 8 ?
              #(cond
                 (s/starts-with? % "11:") "11: 42 31 | 42 11 31"
                 (s/starts-with? % "8:") "8: 42 | 42 8"
                 :else %)
              (concat ["S : 0"] rules-str))))]              ;; numbers can't be rule names t
    (count (filter vector? (map #(modified-parser %) patterns)))))

;; 2
(solve-2 (load-input 19))                                   ;; for some reason this is not martrching, don't