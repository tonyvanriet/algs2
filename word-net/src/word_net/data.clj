(ns word-net.data
  (:gen-class)
  (:require [word-net.digraph :as dig])
  (:require [word-net.sap :as sap]))



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


(defn nouns-in-synsets
  "returns all wordnet nouns in the synsets"
  [synsets]
  (mapcat #(:nouns %) synsets))


(defn noun?
  [word synsets]
  (not (nil? (some #(= word %) (nouns-in-synsets synsets)))))


(defn synset-has-noun?
  [synset noun]
  (some #(= noun %) (:nouns synset)))


(defn get-synset-id-for-noun
  [noun synsets]
  (:id (first (filter #(synset-has-noun? % noun) synsets))))


(defn get-synset-for-id
  [synset-id synsets]
  (first (filter #(= (:id %) synset-id) synsets)))


(defn get-synset-for-noun
  [noun synsets]
  (get-synset-for-id (get-synset-id-for-noun noun synsets) synsets))


(defn println-synset
  [synset & rest]
  (println (:nouns synset) (:id synset)))



(defn distance
  "shortest distance from noun-a to noun-b"
  [noun-a noun-b synsets hypernym-digraph]

  (let [noun-a-synset-id (get-synset-id-for-noun noun-a synsets)
        noun-b-synset-id (get-synset-id-for-noun noun-b synsets)]

    (sap/length noun-a-synset-id noun-b-synset-id hypernym-digraph)))


(defn sap
  "the synset that is the common ancestor of noun-a and b in a
  common ancestral path."
  [noun-a noun-b synsets hypernym-digraph]

  (let [noun-a-synset-id (get-synset-id-for-noun noun-a synsets)
        noun-b-synset-id (get-synset-id-for-noun noun-b synsets)]

    (def ancestor-synset-id
      (sap/ancestor noun-a-synset-id noun-b-synset-id hypernym-digraph))

    (get-synset-for-id ancestor-synset-id synsets)))


(defn random-ancestor-search

  [synsets hypernym-digraph]

;;   (println "")
;;   (println "-------------------------")

  (def nouns (nouns-in-synsets synsets))

  ; pick some random nouns
  (def random-nouns (repeatedly 2 #(nth nouns (rand-int (count nouns)))))
  (def random-synsets (map #(get-synset-for-noun % synsets) random-nouns))

;;   (println-synset (first random-synsets))
;;   (println-synset (second random-synsets))

  (def random-synset-ids (map #(:id %) random-synsets))

  (def noun-a (first random-nouns))
  (def noun-b (second random-nouns))

  (def closest-ancestor
    (sap noun-a noun-b synsets hypernym-digraph))

;;   (println-synset closest-ancestor)

  (def closest-ancestor-distance
    (distance noun-a noun-b synsets hypernym-digraph))

;;   (println closest-ancestor-distance)

  (println [noun-a noun-b closest-ancestor-distance (first (:nouns closest-ancestor))]))
