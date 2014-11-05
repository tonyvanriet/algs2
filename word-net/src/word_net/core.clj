(ns word-net.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [word-net.digraph :as dig])
  (:require [word-net.file-io :as fio]))


(defn -main
  [& args]
  println("running main"))


;; WordNet data type. Implement an immutable data type WordNet with the following API:

;; public class WordNet {

;;    // constructor takes the name of the two input files
;;    public WordNet(String synsets, String hypernyms)

;;    // returns all WordNet nouns
;;    public Iterable<String> nouns()

;;    // is the word a WordNet noun?
;;    public boolean isNoun(String word)

;;    // distance between nounA and nounB (defined below)
;;    public int distance(String nounA, String nounB)

;;    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
;;    // in a shortest ancestral path (defined below)
;;    public String sap(String nounA, String nounB)

;;    // do unit testing of this class
;;    public static void main(String[] args)
;; }



(def synsets
  (fio/get-synsets-from-file (str fio/file-path "synsets6.txt")))


(defn nouns
  "returns all wordnet nouns in the synsets"
  [synsets]
  (mapcat #(:nouns %) synsets))

(nouns synsets)


(defn noun?
  [word synsets]
  (not (nil? (some #(= word %) (nouns synsets)))))


(noun? "a" synsets)
(noun? "z" synsets)
