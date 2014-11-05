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



(defn length
  "returns the length of the shortest ancestral path between
  the vertices within the digraph."

  [v w digraph]
  (+ v w (length digraph)))


(defn ancestor
  "returns the first common ancestor of the vertices within the digraph."

  [v w digraph]

  (/ (+ v w) 2))

