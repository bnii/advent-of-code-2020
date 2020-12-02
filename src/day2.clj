(ns day2
  (:refer-clojure))

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