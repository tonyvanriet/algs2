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


(def test-digraph (dig/build-new-digraph [[1 2] [2 6] [3 4] [3 5]]))


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



;; (defn breadth-first
;;   [digraph]

;;   (def vertex-queue [(first digraph)])

;;   ; visit next vertex in the queue, mark it

;;   (loop [vertex-queue [(first digraph)]
;;          digraph digraph
;;          visited-vertices {}]

;;     (def vertex-visiting (first vertex-queue))

;;     (if-let [vertex-visiting (first vertex-queue)]

;;       (if (some (partial = vertex-visiting) visited-vertices)

;;         (recur (rest vertex-queue)
;;                digraph
;;                visited-vertices)

;;         (recur (conj vertex-queue
;;                      (get-vertices-for-indeces digraph
;;                                                (val vertex-visiting)))
;;                digraph
;;                (rest visited-vertices)))
;;       "done")))



;; (breadth-first test-digraph)
