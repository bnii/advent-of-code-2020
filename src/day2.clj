(ns day2
  (:refer-clojure)
  (:require [common :refer [load-input]]))

(defn extract [st]
  (let [matches (re-matches #"^(\d+)\-(\d+) ([a-zA-Z])\: ([a-zA-Z]*)$" st)]
    {:min  (Integer/parseInt (matches 1))
     :max  (Integer/parseInt (matches 2))
     :char (get (matches 3) 0)
     :pass (matches 4)}))

(defn is-valid-pass [{:keys [min max pass char]}]
  (<= min
      (apply + (map #(if (= char %) 1 0) pass))
      max))

(defn is-valid-pass-2 [{first-point  :min
                        second-point :max
                        pass         :pass
                        test-char    :char}]
  (let [first-matches  (= (get pass (dec first-point)) test-char)
        second-matches (= (get pass (dec second-point)) test-char)]
    (or
      (and first-matches (not second-matches))
      (and second-matches (not first-matches)))))

(->>
  (load-input 2)
  (map extract)
  (map is-valid-pass)
  (filter (comp identity))
  count)

(->>
  (load-input 2)
  (map extract)
  (map is-valid-pass-2)
  (filter (comp identity))
  count)
