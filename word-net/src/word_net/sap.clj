(ns word-net.sap
  (:gen-class)
  (:require [word-net.digraph :as dig]))


;; SAP data type. Implement an immutable data type SAP with the following API:

;; public class SAP {

;;    // constructor takes a digraph (not necessarily a DAG)
;;    public SAP(Digraph G)

;;    // length of shortest ancestral path between v and w; -1 if no such path
;;    public int length(int v, int w)

;;    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
;;    public int ancestor(int v, int w)

;;    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
;;    public int length(Iterable<Integer> v, Iterable<Integer> w)

;;    // a common ancestor that participates in shortest ancestral path; -1 if no such path
;;    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)

;;    // do unit testing of this class
;;    public static void main(String[] args)
;; }


(def test-digraph (dig/build-new-digraph [[1 2] [2 6] [6 7] [3 4] [3 6] [4 2] [4 6]]))


(defn length

  "returns the length of the shortest ancestral path between
  the vertices within the digraph."
  [v w digraph]

  ; breadth-first from each vertex, marking the distance to the vertex
  ; as we go. then look through those distances for the smallest sum.




  )



(defn ancestor
  "returns the first common ancestor of the vertices within the digraph."

  [v w digraph]

  (/ (+ v w) 2))



(defn get-vertices-for-indeces
  [digraph indeces]
  (into {}
        (filter
         (fn [vertex]
           (some (partial = (key vertex)) indeces))
         digraph)))


(get-vertices-for-indeces test-digraph #{2 3})



(defn breadth-first
  [digraph]

  (loop [vertices-to-visit (conj {} (first digraph))
         visited-vertices {}
         de-infinitizer 0]

    (if (or (empty? vertices-to-visit) (> de-infinitizer 100))

      visited-vertices

      (do
        (let [vertex-visiting (first vertices-to-visit)]

          (if (some (partial = (first vertex-visiting)) (map (partial key) visited-vertices))

            (do
              (recur (rest vertices-to-visit)
                     visited-vertices
                     (inc de-infinitizer)))

            (do
              (recur (merge (into {} (rest vertices-to-visit))
                            (get-vertices-for-indeces digraph (val vertex-visiting)))
                     (conj visited-vertices vertex-visiting)
                     (inc de-infinitizer)))))))))


(breadth-first test-digraph)

