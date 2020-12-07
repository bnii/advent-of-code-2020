#!/usr/bin/env bb

(require '[babashka.curl :as curl])
(require '[clojure.java.io :as io])

(def day (first *command-line-args*))
(def session (System/getenv "AOC_SESSION"))
(def outfile (str "src/day" day "-input.txt"))

(def txt (:body (curl/get
                  (str "https://adventofcode.com/2020/day/" day "/input")
                  {:headers
                   {"Cookie" (str "session=" session)}})))

(with-open [w (io/writer outfile)]
  (.write w txt))