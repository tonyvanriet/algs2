(ns word-net.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [word-net.digraph :as dig]))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



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


(def synsets-file-name "synsets6.txt")
(def hypernyms-file-name "hypernyms6TwoAncestors.txt")

(def file-path "src/word_net/word-net-testing/")
(def synsets-file-path (str file-path synsets-file-name))
(def hypernyms-file-path (str file-path hypernyms-file-name))


(defn lazy-file-line-seq [file]
  "returns a lazy sequence of lines from the given file"
  (letfn [(helper [rdr]
                  (lazy-seq
                    (if-let [line (.readLine rdr)]
                      (cons line (helper rdr))
                      (do (.close rdr) nil))))]
    (helper (clojure.java.io/reader file))))


(defn get-synsets-from-file

  [file-name]

  (loop [synsets-file-lines (lazy-file-line-seq file-name)
         synsets []]
    (if (empty? synsets-file-lines)
      synsets
      (do
        ;(println (first synsets-file-lines))
        (let [[id nouns-raw gloss] (str/split (first synsets-file-lines) #",")]
          (let [nouns (str/split nouns-raw #" ")]
            (recur (rest synsets-file-lines)
                   (conj synsets {:id id :nouns nouns}))))))))


(get-synsets-from-file synsets-file-path)


(defn get-hypernym-digraph-from-file

  [file-name]

  (loop [hypernyms-file-lines (lazy-file-line-seq file-name)
         digraph (dig/build-new-digraph)]

    (if (empty? hypernyms-file-lines)

      digraph

      (do
        (let [ids (str/split (first hypernyms-file-lines) #",")
              synset-id (first ids)
              hypernym-ids (rest ids)]

          (def digraph-after-edges
            (reduce (fn [digraph hypernym-id]
                      (dig/add-edge synset-id hypernym-id digraph))
                    digraph
                    hypernym-ids))

          (recur (rest hypernyms-file-lines) digraph-after-edges))))))


(get-hypernym-digraph-from-file hypernyms-file-path)
