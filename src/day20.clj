(ns day20
  (:refer-clojure)
  (:require [common :refer [slurp-day]]
            [clojure.string :as s]
            [clojure.math.combinatorics :as combo]))


;; get-tiles
;; process-tile
;; matrix of X _ ; vector oftiles with map wih id,content, vector of of tuples,


#_"
- kell valami amivel vvaliidaljuk a 3x3-as setupot
- kell valami ami egy matrix minden rotalasat viisszaadja
- kell valami ami visszaadja a negy oldalt egy matrixhoz
- ami a rotalasokat visszaadja, az mar adhatja vissza a negy feltetelezett oldal sequencee-et
- "

(defn get-tile [input]
  (let [[header & tile] (s/split-lines input)]
    [(map s/trim tile) (Integer/parseInt (get
                                           (re-matches #"^.* (.\d+):$" header)
                                           1))]))

(defn get-tiles [input]
  (map get-tile
       (s/split input #"\n\n")))

(defn rotate-right [matrix]
  (mapv reverse (apply mapv vector matrix)))

(comment
  (rotate-right [[1 2] [3 4]]))

(defn rotations-from [matrix]
  (take 4 (iterate rotate-right matrix)))

(defn all-variants [matrix]
  (distinct (mapcat
              rotations-from
              [matrix (reverse matrix) (map reverse matrix)])))

(defn norm-edge [edge]
  (if (> (compare (vec edge) (vec (reverse edge))) 0)
    edge
    (vec (reverse edge))))

(defn edges [matrix]
  (mapv norm-edge [(vec (first matrix))
                   (map last matrix)
                   (vec (last matrix))
                   (map first matrix)]))



(comment
  (map edges (all-variants [[1 2 3] [4 5 6] [7 8 9]]))
  (all-variants [[1 2] [3 4]]))

(comment
  (rotations-from [[1 2] [3 4]]))

(comment
  (def one-tile "Tile 2437:\n  ..##.#.#.#\n  ..#.#....#\n  ...#......\n  #.....#..#\n  .#....#..#\n  ##..##....\n  #.#......#\n  .....#..#.\n  ....##.###\n  ..#####.##")
  (def example "Tile 2311:\n..##.#..#.\n##..#.....\n#...##..#.\n####.#...#\n##.##.###.\n##...#.###\n.#.#.#..##\n..#....#..\n###...#.#.\n..###..###\n\nTile 1951:\n#.##...##.\n#.####...#\n.....#..##\n#...######\n.##.#....#\n.###.#####\n###.##.##.\n.###....#.\n..#.#..#.#\n#...##.#..\n\nTile 1171:\n####...##.\n#..##.#..#\n##.#..#.#.\n.###.####.\n..###.####\n.##....##.\n.#...####.\n#.##.####.\n####..#...\n.....##...\n\nTile 1427:\n###.##.#..\n.#..#.##..\n.#.##.#..#\n#.#.#.##.#\n....#...##\n...##..##.\n...#.#####\n.#.####.#.\n..#..###.#\n..##.#..#.\n\nTile 1489:\n##.#.#....\n..##...#..\n.##..##...\n..#...#...\n#####...#.\n#..#.#.#.#\n...#.#.#..\n##.#...##.\n..##.##.##\n###.##.#..\n\nTile 2473:\n#....####.\n#..#.##...\n#.##..#...\n######.#.#\n.#...#.#.#\n.#########\n.###.#..#.\n########.#\n##...##.#.\n..###.#.#.\n\nTile 2971:\n..#.#....#\n#...###...\n#.#.###...\n##.##..#..\n.#####..##\n.#..####.#\n#..#.#..#.\n..####.###\n..#.#.###.\n...#.#.#.#\n\nTile 2729:\n...#.#.#.#\n####.#....\n..#.#.....\n....#..#.#\n.##..##.#.\n.#.####...\n####.#.#..\n##.####...\n##..#.##..\n#.##...##.\n\nTile 3079:\n#.#.#####.\n.#..######\n..#.......\n######....\n####.#..#.\n.#...#.##.\n#.#####.##\n..#.###...\n..#.......\n..#.###...")
  (get-tile one-tile)
  (slurp-day 20)
  (get-tiles (slurp-day 20))
  (count (get-tiles (slurp-day 20))))

(comment
  (count (combo/combinations (range 144) 9))
  (edges [[1 2 3] [4 5 6] [7 8 9]]))

(defn edge-to-number [edge]
  (Integer/parseInt
    (apply str (map #(if (= \# %) \1 \0) edge))
    2))

(comment
  (edge-to-number "##.#"))

(defn edge-to-normalized [edge]
  (let [n  (edge-to-number edge)
        nr (edge-to-number (reverse edge))]
    (min n nr)))

(comment
  (edge-to-normalized "#.##"))

(defn t->edges-map [tiles]
  (->> tiles
       (map (fn [tile] [tile (edges tile)]))))

(def tile-to-edge-map (into {} (t->edges-map (map first (get-tiles (slurp-day 20))))))

(def edge-to-tile-map
  (->> (slurp-day 20)
       (get-tiles)
       (map first)
       (mapcat (fn [tile] (map (fn [edge] [tile edge]) (edges tile))))
       (reduce (fn [acc [tile edge]]
                 (update
                   acc
                   edge
                   (fn [old-tiles]
                     (conj old-tiles tile))))
               {})))

(def edge-count
  (into {} (map (fn [[edge tiles]] [edge (count tiles)]) edge-to-tile-map)))

;;1
(->> tile-to-edge-map
     (map (fn [[tile edges]]
            [tile (apply + (map edge-count edges))]))
     (filter (fn [[_ c]] (= c 6)))
     (map first)
     (keep #(get (->> (slurp-day 20) (get-tiles) (into {})) %))
     (apply *))

;;;;
(comment
  (tiles->edges-map (map first (get-tiles (slurp-day 20)))))

;     (map (fn [[tile edges]]) map edge-to-normalized %))))


(defn tile-edge [input]
  (->> input
       (get-tiles)
       (map first)
       tiles->edges-map))



(defn edges-per-tiles [input])

(comment
  (input-to-edges (slurp-day 20))
  (slurp-day 20))

