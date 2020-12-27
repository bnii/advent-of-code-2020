(ns day23
  (:refer-clojure))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(comment
  (def example-data "389125467"))

(def real-input "469217538")

;; in theory, other/better implementation of CircularList could provide better performance.
;; in practice this has flaws;
;; (1) insert after is not dependent on the implementation -- it shouldnt belong here
;; (2) feels fishy to doesn't maintain an invariant for the "current element". maintaining the current element
;; also doesn't feel to be part of the datastructure
;; (3) not only this Map-backed implementation is responsible for the poor performance

(defprotocol CircularList
  (get-next [this elem])
  (insert-after [this after-elem new-elem])
  (set-next [this after-elem new-elem])
  (set-current [this current-elem]))

(defrecord MapBackedCircularList [m current-cup size]
  CircularList
  (get-next [this elem]
    (if-let [next-elem (get (:m this) elem)]
      next-elem
      (inc ^int elem)))
  (insert-after [this after-elem new-elem]
    (let [next-elem (get-next this after-elem)]
      (-> this
          (set-next after-elem new-elem)
          (set-next new-elem next-elem))))
  (set-next [this after-elem new-elem]
    (->MapBackedCircularList
      (if (= (inc ^int after-elem) new-elem)
        (dissoc (:m this) after-elem)
        (assoc (:m this) after-elem new-elem))
      current-cup
      size))
  (set-current [this current-elem]
    (->MapBackedCircularList (:m this) current-elem (:size this))))


(defn get-circular-map
  ([s]
   (let [parsed (map #(Integer/parseInt (str %)) s)
         f      (first parsed)
         l      (last parsed)
         pp     (map vec (partition 2 1 parsed))]
     (->MapBackedCircularList
       (into {l f} pp)
       f
       (count s))))
  ([s c]
   (let [parsed (map #(Integer/parseInt (str %)) s)
         f      (first parsed)
         l      (last parsed)
         pp     (map vec (partition 2 1 parsed))]
     (->MapBackedCircularList
       (into {l (inc (count s)), c f} pp)
       f
       c))))

(comment
  (def example-data-parsed (get-circular-map example-data))
  (def example-data-parsed-big (get-circular-map example-data 1000000)))


(defn cup-seq [{size :size current-cup :current-cup :as cups}]
  (take size (iterate (partial get-next cups) current-cup)))

(comment
  (cup-seq example-data-parsed))


(defn dec-till-not-in [v-set n]
  (first
    (keep
      #(when-not (v-set %) %)
      (range (dec ^int n) -1 -1))))

(comment
  (dec-till-not-in #{123 124} 4)
  (dec-till-not-in #{123 125} 125)
  (dec-till-not-in #{1 8 9} 2))


(defn dest-cup [cups]
  (let [current-cup    (:current-cup cups)
        pickup         (take 3 (rest (iterate (partial get-next cups) current-cup)))
        pickup-set     (set pickup)
        without-pickup (set-next cups current-cup (get-next cups (last pickup)))
        dest-candid    (dec-till-not-in pickup-set current-cup)
        dest           (if (< ^int dest-candid 1)
                         (dec-till-not-in pickup-set (inc (:size cups)))
                         dest-candid)
        next-cups      (first (reduce
                                (fn [[acc prev] curr] [(insert-after acc prev curr) curr])
                                [without-pickup dest]
                                pickup))]
    (set-current next-cups (get-next next-cups current-cup))))

(comment
  (dest-cup example-data-parsed)
  (cup-seq (dest-cup example-data-parsed)))


(defn get-nth [circular-list n]
  (nth (iterate dest-cup circular-list) n))

(comment
  (get-nth example-data-parsed 10)
  (map #(cup-seq (get-nth example-data-parsed %)) (range 0 10)))

(defn solve-1 [result]
  (apply str (let [res-seq (cup-seq result)]
               (take
                 (dec (count res-seq))
                 (rest (drop-while
                         #(not= % 1)
                         (cycle res-seq)))))))

(comment
  (solve-1 (get-nth example-data-parsed 10)))

;;1
(solve-1 (get-nth (get-circular-map real-input) 100))

(comment
  (time (def calc-big-example
          (get-nth (get-circular-map example-data 1000000) 10000000))))


(defn solve-2 [result]
  (let [n (get-next result 1)]
    (* ^int n ^int (get-next result n))))

(comment
  (solve-2 calc-big-example))

;;2

(defn get-result []
  (get-nth (get-circular-map real-input 1000000) 10000000))

(solve-2 (get-result))