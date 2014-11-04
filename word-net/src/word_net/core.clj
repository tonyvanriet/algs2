(ns word-net.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require word-net.digraph))


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


(def synsets-file-name "synsets3.txt")
(def hypernyms-file-name "hypernyms6TwoAncestors.txt")

(def file-path "src/word_net/word-net-testing/")
(def synsets-file-path (str file-path synsets-file-name))
(def hypernyms-file-path (str file-path hypernyms-file-name))




(defn lazy-file-lines [file]
  "returns a lazy sequence of lines from the given file"
  (letfn [(helper [rdr]
                  (lazy-seq
                    (if-let [line (.readLine rdr)]
                      (cons line (helper rdr))
                      (do (.close rdr) nil))))]
         (helper (clojure.java.io/reader file))))




(defn get-synsets-from-file

  [file-name]

  (doseq [line (lazy-file-lines file-name)]
    (println "sadf" line)))


(get-synsets-from-file synsets-file-path)
