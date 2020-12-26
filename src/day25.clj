(ns day25
  (:refer-clojure)
  (:require [common :refer [load-input-int]]))


(comment
  (def example-data [5764801 17807724]))

(def modulus 20201227)
(def base 7)

(defn transform
  ([subject loop-size]
   (transform 1 subject loop-size))
  ([value subject loop-size]
   (if (= loop-size 0)
     value
     (recur
       (mod (* value subject) modulus)
       subject
       (dec loop-size)))))

(comment
  (transform 7 1)
  (transform 7 8))


(defn get-loop-size
  ([target]
   (reduce
     (fn [value loop-size]
       (if (= value target)
         (reduced loop-size)
         (mod (* value base) modulus)))
     1
     (range))))

(comment
  (get-loop-size (example-data 0))
  (get-loop-size (example-data 1)))


(defn solve-1 [input]
  (let [[door-pubkey card-pubkey] input
        card-loopsize (get-loop-size card-pubkey)]
    (transform door-pubkey card-loopsize)))

(comment
  (solve-1 example-data))


;;1
(time (solve-1 (load-input-int 25)))
