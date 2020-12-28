(defproject advent-of-code-2020 "0.1.0-SNAPSHOT"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ubergraph "0.8.2"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [com.clojure-goes-fast/clj-async-profiler "0.4.1"]
                 [org.clojure/tools.namespace "1.1.0"]
                 [quil "3.1.0"]
                 [instaparse "1.4.10"]]

  :plugins [[lein-marginalia "0.9.1"]]
  :profiles {:dev {:dependencies [[lambdaisland/kaocha "1.0.732"]]}}
  :aliases {"kaocha" ["run" "-m" "kaocha.runner"]}
  :jvm-opts ["-Djdk.attach.allowAttachSelf"])


