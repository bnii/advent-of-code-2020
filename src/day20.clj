(ns day20
  (:refer-clojure)
  (:require [common :refer [slurp-day]]
            [clojure.string :as s]))


(defn get-tile [input]
  (let [[header & tile] (s/split-lines input)]
    [(map s/trim tile) (Integer/parseInt (get
                                           (re-matches #"^.* (.\d+):$" header)
                                           1))]))
(defn get-tiles [input]
  (map get-tile
       (s/split input #"\n\n")))

(defn rotate-right [matrix]
  (mapv #(vec (reverse %)) (apply mapv vector matrix)))

(comment
  (rotate-right [[1 2] [3 4]]))

(defn rotations-from [matrix]
  (take 4 (iterate rotate-right matrix)))

(defn all-variants [matrix]
  (distinct (mapcat
              rotations-from
              [matrix (vec (reverse matrix)) (map reverse matrix)])))

(defn norm-edge [edge]
  (if (> (compare (vec edge) (vec (reverse edge))) 0)
    (vec edge)
    (vec (reverse edge))))

(defn edges [matrix]
  (mapv norm-edge [(vec (first matrix))
                   (mapv last matrix)
                   (vec (last matrix))
                   (mapv first matrix)]))

(comment
  (map edges (all-variants [[1 2 3] [4 5 6] [7 8 9]]))
  (all-variants [[1 2] [3 4]]))

(comment
  (rotations-from [[1 2] [3 4]]))

(def example "Tile 2311:\n..##.#..#.\n##..#.....\n#...##..#.\n####.#...#\n##.##.###.\n##...#.###\n.#.#.#..##\n..#....#..\n###...#.#.\n..###..###\n\nTile 1951:\n#.##...##.\n#.####...#\n.....#..##\n#...######\n.##.#....#\n.###.#####\n###.##.##.\n.###....#.\n..#.#..#.#\n#...##.#..\n\nTile 1171:\n####...##.\n#..##.#..#\n##.#..#.#.\n.###.####.\n..###.####\n.##....##.\n.#...####.\n#.##.####.\n####..#...\n.....##...\n\nTile 1427:\n###.##.#..\n.#..#.##..\n.#.##.#..#\n#.#.#.##.#\n....#...##\n...##..##.\n...#.#####\n.#.####.#.\n..#..###.#\n..##.#..#.\n\nTile 1489:\n##.#.#....\n..##...#..\n.##..##...\n..#...#...\n#####...#.\n#..#.#.#.#\n...#.#.#..\n##.#...##.\n..##.##.##\n###.##.#..\n\nTile 2473:\n#....####.\n#..#.##...\n#.##..#...\n######.#.#\n.#...#.#.#\n.#########\n.###.#..#.\n########.#\n##...##.#.\n..###.#.#.\n\nTile 2971:\n..#.#....#\n#...###...\n#.#.###...\n##.##..#..\n.#####..##\n.#..####.#\n#..#.#..#.\n..####.###\n..#.#.###.\n...#.#.#.#\n\nTile 2729:\n...#.#.#.#\n####.#....\n..#.#.....\n....#..#.#\n.##..##.#.\n.#.####...\n####.#.#..\n##.####...\n##..#.##..\n#.##...##.\n\nTile 3079:\n#.#.#####.\n.#..######\n..#.......\n######....\n####.#..#.\n.#...#.##.\n#.#####.##\n..#.###...\n..#.......\n..#.###...")


(comment
  (def one-tile "Tile 2437:\n  ..##.#.#.#\n  ..#.#....#\n  ...#......\n  #.....#..#\n  .#....#..#\n  ##..##....\n  #.#......#\n  .....#..#.\n  ....##.###\n  ..#####.##")
  (def example "Tile 2311:\n..##.#..#.\n##..#.....\n#...##..#.\n####.#...#\n##.##.###.\n##...#.###\n.#.#.#..##\n..#....#..\n###...#.#.\n..###..###\n\nTile 1951:\n#.##...##.\n#.####...#\n.....#..##\n#...######\n.##.#....#\n.###.#####\n###.##.##.\n.###....#.\n..#.#..#.#\n#...##.#..\n\nTile 1171:\n####...##.\n#..##.#..#\n##.#..#.#.\n.###.####.\n..###.####\n.##....##.\n.#...####.\n#.##.####.\n####..#...\n.....##...\n\nTile 1427:\n###.##.#..\n.#..#.##..\n.#.##.#..#\n#.#.#.##.#\n....#...##\n...##..##.\n...#.#####\n.#.####.#.\n..#..###.#\n..##.#..#.\n\nTile 1489:\n##.#.#....\n..##...#..\n.##..##...\n..#...#...\n#####...#.\n#..#.#.#.#\n...#.#.#..\n##.#...##.\n..##.##.##\n###.##.#..\n\nTile 2473:\n#....####.\n#..#.##...\n#.##..#...\n######.#.#\n.#...#.#.#\n.#########\n.###.#..#.\n########.#\n##...##.#.\n..###.#.#.\n\nTile 2971:\n..#.#....#\n#...###...\n#.#.###...\n##.##..#..\n.#####..##\n.#..####.#\n#..#.#..#.\n..####.###\n..#.#.###.\n...#.#.#.#\n\nTile 2729:\n...#.#.#.#\n####.#....\n..#.#.....\n....#..#.#\n.##..##.#.\n.#.####...\n####.#.#..\n##.####...\n##..#.##..\n#.##...##.\n\nTile 3079:\n#.#.#####.\n.#..######\n..#.......\n######....\n####.#..#.\n.#...#.##.\n#.#####.##\n..#.###...\n..#.......\n..#.###...")
  (get-tile one-tile)
  (slurp-day 20)
  (get-tiles (slurp-day 20))
  (count (get-tiles (slurp-day 20))))


(defn tile->normailzed-edges-map [tiles]
  (->> tiles
       (map (fn [tile] [tile (edges tile)]))))

(comment
  (def tile-to-edge-map (into {} (tile->normailzed-edges-map (map first (get-tiles (slurp-day 20)))))))

(def edge-to-tile-map
  (->> #_ (slurp-day 20)
       example
       (get-tiles)
       (map first)
       (mapcat (fn [tile] (map (fn [edge] [tile edge]) (edges tile))))
       (reduce (fn [acc [tile edge]]
                 (update
                   acc
                   edge
                   (fn [old-tiles]
                     (conj old-tiles (vec (mapv vec tile))))))
               {})))

(def edge-count
  (into {} (map (fn [[edge tiles]] [edge (count tiles)]) edge-to-tile-map)))

;;1


(def one-sarok (->> tile-to-edge-map
                    (map (fn [[tile edges]]
                           [tile (apply + #p (map edge-count edges))]))
                    (filter (fn [[_ c]] (= c 6)))
                    (map first)
                    (first)))

;; a saroknak keressunk egy elet amin el lehet indulni

(def egy-tobbes-el-az-elso-sarokbol
  (first (keep #(when (= (edge-count %) 2) %) (edges one-sarok))))

;; beforgatjuk hogy also sor legyen ez
(def beforgatott-sarok-hogy-az-also-el-tobbes-legyen
  (first (filter #(= (last %) egy-tobbes-el-az-elso-sarokbol) (all-variants one-sarok))))

;;

;; kovetkezo megkeresese
(filter
  #(= (first %) egy-tobbes-el-az-elso-sarokbol)
  (all-variants (first (remove #(= one-sarok %) (edge-to-tile-map egy-tobbes-el-az-elso-sarokbol)))))

;; tehat legyen egy fuggvenyunk, ami a legfelso, jol beforgatott tile alapjan meghatarozza az egesz sort

(defn get-tile-below [tile]
  (let [last-row                   (last tile)
        last-normalized            (norm-edge last-row)
        tiles-with-this-edge       (edge-to-tile-map (vec last-normalized))
        all-varians-for-both-tiles (map all-variants tiles-with-this-edge)
        next-tile-all-variants     (first (remove #(some (fn [variant] (= tile variant)) %)
                                                  all-varians-for-both-tiles))
        next-tile                  (some #(when (= (first %) last-row) %)
                                         next-tile-all-variants)]

    next-tile))

(comment
  (get-tile-below beforgatott-sarok-hogy-az-also-el-tobbes-legyen))

;;✅
(defn get-column-from-top-tile [tile]
  (take-while
    identity
    (iterate get-tile-below tile)))

(comment
  (get-column-from-top-tile beforgatott-sarok-hogy-az-also-el-tobbes-legyen))


(defn get-tile-right [tile]
  (when tile
    (let [right-rotated-original (rotate-right tile)
          matching-tile-rotated  (get-tile-below right-rotated-original)]
      (when matching-tile-rotated
        (nth
          (iterate rotate-right matching-tile-rotated)
          3)))))

(comment
  (get-tile-right beforgatott-sarok-hogy-az-also-el-tobbes-legyen))


(defn get-top-row-from-leftmost-top-tile [tile]
  (take-while
    identity
    (iterate get-tile-right tile)))

(comment
  (get-top-row-from-leftmost-top-tile
    beforgatott-sarok-hogy-az-also-el-tobbes-legyen))

(defn get-assembled-matrix-from-topleft-tile [tile]
  (->> tile
       get-top-row-from-leftmost-top-tile
       (mapv get-column-from-top-tile)))

(comment
  (def assembed-matrix (get-assembled-matrix-from-topleft-tile beforgatott-sarok-hogy-az-also-el-tobbes-legyen)))

(defn remove-edges [tile-matrix]
  (mapv
    (fn [column]
      (mapv
        (fn [tile]
          (->> tile
               butlast
               rest
               (mapv
                 (fn [row] (vec (rest (butlast row)))))))
        column))
    tile-matrix))

(comment
  (remove-edges [[[[1 2 3]
                   [4 5 6]
                   [7 8 9]]]])
  (def removed-edges (remove-edges assembed-matrix)))
;;;eddig jo asszem!

(defn concat-column [column]
  (let [transposed-matrices (mapv #(apply mapv vector %) column)
        wtf                 (apply mapv vector transposed-matrices)]
    (apply mapv vector (mapv first (mapv
                                     #(vector (apply concat %))
                                     wtf)))))

(defn concat-columns [tile-matrix]
  (apply map concat (apply map vector (map concat-column tile-matrix))))

(comment
  (concat-column
    [[[1 2 3]
      [4 5 6]
      [7 8 9]]
     [[10 11 12]
      [13 14 15]
      [16 17 18]]])
  (concat-columns [[[[1 2 3]
                     [4 5 6]
                     [7 8 9]]
                    [[10 11 12]
                     [13 14 15]
                     [16 17 18]]]
                   [[[1 2 3]
                     [4 5 6]
                     [7 8 9]]
                    [[10 11 12]
                     [13 14 15]
                     [16 17 18]]]]))



(def assembled
  (->>
    removed-edges
    (map (partial reduce concat))
    (reduce (partial mapv (comp vec concat)))))


(def monster (mapv vec (s/split-lines "                  # \n#    ##    ##    ###\n #  #  #  #  #  #   ")))

(defn monster-at [image x y]
  (let [coords (remove nil? (for [xm
                                  (range (count (first monster)))
                                  ym
                                  (range (count monster))]
                              (when
                                (= (get-in monster [ym xm]) \#)
                                [ym xm])))]

    (->>  coords
         (map (fn [[xm ym]] [(+ xm x) (+ ym y)]))
         (map #(get-in image %))
         (every? #(= \#   %)))))

(monster-at monster 0 0)

(def monster-pixelno (count (remove #(not= % \#) (flatten monster))))

(def sum-pixelno (count (remove #(not= % \#) (flatten assembled))))

(defn count-monsters [image]
  (count (remove false? (for [x (range (- (count (first image))
                                          (count (first monster))))
                              y (range (- (count image)
                                          (count monster)))]
                          (monster-at image y x)))))

(count-monsters assembled)

(def monster-count (apply max (map count-monsters (all-variants assembled))))

(- sum-pixelno (* (+ 5 monster-count) monster-pixelno))

