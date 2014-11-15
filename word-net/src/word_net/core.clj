(ns word-net.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [word-net.digraph :as dig])
  (:require [word-net.file-io :as fio])
  (:require [word-net.sap :as sap])
  (:require [word-net.data :as data])
  (:require [criterium.core :as crit]))


(def synsets-file-name "synsets.txt")


(defn random-ancestor-search-on-file

  [hypernyms-file-name]

  (let [synsets (fio/get-synsets-from-file
                 (str fio/file-path synsets-file-name))
        hypernym-digraph (fio/get-hypernym-digraph-from-file
                          (str fio/file-path hypernyms-file-name))]

    (data/random-ancestor-search synsets hypernym-digraph)))



(defn -main
  [& args]

  (def synsets-file-name (or (first args) synsets-file-name))
  (def hypernyms-file-name (or (second args) "hypernyms.txt"))

  (def synsets
    (fio/get-synsets-from-file (str fio/file-path synsets-file-name)))

  (def hypernym-digraph
    (fio/get-hypernym-digraph-from-file (str fio/file-path hypernyms-file-name)))

  ;(repeatedly 100 #(time (data/random-ancestor-search synsets hypernym-digraph)))

  ;(time (data/random-ancestor-search synsets hypernym-digraph))

  ;(crit/bench (random-ancestor-search-on-file "hypernyms.txt"))

  (crit/bench (random-ancestor-search-on-file "hypernyms100k.txt"))

  (crit/bench (random-ancestor-search-on-file "hypernyms200k.txt"))

  (crit/bench (random-ancestor-search-on-file "hypernyms300k.txt")))




