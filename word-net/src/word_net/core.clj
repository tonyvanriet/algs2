(ns word-net.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [word-net.digraph :as dig])
  (:require [word-net.file-io :as fio])
  (:require [word-net.sap :as sap]))


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

(def hypernym-digraph
  (fio/get-hypernym-digraph-from-file (str fio/file-path "hypernyms6TwoAncestors.txt")))


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



(defn synset-has-noun?
  [synset noun]
  (some #(= noun %) (:nouns synset)))


(defn get-synset-id-for-noun
  [noun synsets]
  (:id (first (filter #(synset-has-noun? % noun) synsets))))


(get-synset-id-for-noun "b" synsets)



(defn distance
  "shortest distance from noun-a to noun-b"
  [noun-a noun-b synsets hypernym-digraph]

  (let [noun-a-synset-id (get-synset-id-for-noun noun-a synsets)
        noun-b-synset-id (get-synset-id-for-noun noun-b synsets)]

    (- noun-b-synset-id noun-a-synset-id)))


(distance "b" "c" synsets hypernym-digraph)



(defn sap
  "the synset that is the common ancestor of noun-a and b in a
  common ancestral path."
  [noun-a noun-b synsets hypernym-digraph]

  (let [noun-a-synset-id (get-synset-id-for-noun noun-a synsets)
        noun-b-synset-id (get-synset-id-for-noun noun-b synsets)]

    (- noun-b-synset-id noun-a-synset-id)))



