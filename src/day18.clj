(ns day18
  (:refer-clojure)
  (:require [common :refer [load-input]]))

(defn flat-eval-in-order [literals]
  (reduce
    (fn [acc [op operand-next]]
      ((eval op) acc operand-next))
    (first literals)
    (partition 2 (rest literals))))

(comment
  (flat-eval-in-order [1 + 2 * 3]))

(defn flat-eval-reverse-prio [literals]
  (->> literals
       (partition-by #(= '* %))
       (remove #(= ['*] %))
       (map (fn [v] (remove #(= '+ %) v)))
       (map #(apply + %))
       (apply *)))

(comment
  (flat-eval-reverse-prio '(1 * 2 + 4 * 3 + 4 + 5 * 9)))

(defn evl [flat-fn literals]
  (clojure.walk/postwalk
    (fn [x]
      (if (seqable? x)
        (flat-fn (map (partial evl flat-fn) x))
        x))
    literals))

(comment
  (evl flat-eval-in-order (read-string (str "(" "5 + 8 * ((6 * 5 * 8 + 5 + 8) * 7 + 3)" ")")))
  (evl flat-eval-reverse-prio (read-string (str "(" "5 + 8 * ((6 * 5 * 8 + 5 + 8) * 7 + 3)" ")"))))

(defn solve [input flat-fn]
  (apply + (map
             #(evl flat-fn (read-string (str "(" % ")")))
             input)))

(comment
  (solve example-data flat-eval-reverse-prio))

;;1
(solve (load-input 18) flat-eval-in-order)

;;2
(solve (load-input 18) flat-eval-reverse-prio)