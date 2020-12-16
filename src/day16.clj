(ns day16
  (:refer-clojure)
  (:require [common :refer [load-input]]
            [clojure.string :as s]))

(def example-data (s/split-lines "class: 1-3 or 5-7\nrow: 6-11 or 33-44\nseat: 13-40 or 45-50\n\nyour ticket:\n7,1,14\n\nnearby tickets:\n7,3,47\n40,4,50\n55,2,20\n38,6,12"))

(defn parse-fields
  [field-strs]
  (map (fn [field-str]
         (let [[field-name intervals-str] (s/split field-str #": ")
               interval-strs  (s/split intervals-str #" or ")
               intervals-vecs (map
                                #(map
                                   (fn [s] (Long/parseLong s))
                                   (s/split % #"-"))
                                interval-strs)]
           [field-name intervals-vecs]))
       field-strs))

(defn parse-data [input-lines]
  (let [[fields-str _ [_ my-ticket-str] _ [_ & nearby-ticket-strs]]
        (partition-by #(= % "") input-lines)
        my-ticket
        (map #(Long/parseLong %) (s/split my-ticket-str #","))]
    {:my-ticket      my-ticket
     :fields         (parse-fields fields-str)
     :nearby-tickets (map
                       (fn [line] (map #(Long/parseLong %) (s/split line #",")))
                       nearby-ticket-strs)}))

(defn value-valid? [aggr-rules n]
  (some
    (fn [[l h]]
      (<= l n h))
    aggr-rules))

(defn all-rules [fields]
  (apply concat (map (fn [[_ v]] v) fields)))

(defn filter-valid-tickets [{fields :fields tickets :nearby-tickets}]
  (filter
    #(every?
       (fn [v] (value-valid? (all-rules fields) v))
       %)
    tickets))

(defn solve-1 [parsed-input]
  (apply + (map
             #(if (value-valid? (all-rules (:fields parsed-input)) %) 0 %)
             (apply concat (:nearby-tickets parsed-input)))))

;;1
(solve-1 (parse-data (load-input 16)))

;;;;;;;;;;;;

(defn valid-fields-for-ticket [fields ticket]
  (map
    (fn [value]
      (keep
        (fn [[rule-name intervals]]
          (when
            (some
              (fn [[l h]]
                (<= l value h))
              intervals)
            rule-name))
        fields))
    ticket))

(defn determine-column-field [column-values]
  (apply
    clojure.set/intersection
    (map set column-values)))


(defn possible-fields-per-column [fields valid-tickets]
  (map
    determine-column-field
    (apply map vector (map
                        #(valid-fields-for-ticket fields %)
                        valid-tickets))))

(defn fixed-column-order [parsed-input]
  (loop [possible-fields (possible-fields-per-column
                           (:fields parsed-input)
                           (filter-valid-tickets parsed-input))
         cleaned-fields  #{}]
    (if (= 1 (apply max (map count possible-fields)))
      (map first possible-fields)
      (let [single  (first (first (filter
                                    #(and
                                       (= (count %) 1)
                                       (not (cleaned-fields (first %))))
                                    possible-fields)))
            removed (map
                      #(if (= (count %) 1)
                         %
                         (disj % single))
                      possible-fields)]
        (recur removed (conj cleaned-fields single))))))

(defn solve-2 [parsed-input]
  (->> (interleave
         (fixed-column-order parsed-input)
         (:my-ticket parsed-input))
       (partition 2)
       (keep (fn [[field value]]
               (when (clojure.string/starts-with? field "departure")
                 value)))
       (apply *)))

;;2
(solve-2 (parse-data (load-input 16)))
