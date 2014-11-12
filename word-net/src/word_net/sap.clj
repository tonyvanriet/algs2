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


(def test-digraph (dig/build-new-digraph [[1 2] [2 6] [6 7] [3 4] [3 6] [4 2] [4 6] [7 8] [8 2]]))


(defn get-vertices-for-indeces
  [digraph indeces]
  (into {}
        (filter
         (fn [vertex]
           (some (partial = (key vertex)) indeces))
         digraph)))


(get-vertices-for-indeces test-digraph #{2 3})



(defn shortest-distances

  "uses a breadth-first search to compute the shortest-distance to each vertex
  starting from the given vertex. the results are returns as a map keyed by the vertex
  id."

  [digraph starting-vertex-id]

  (println "running shortest distance starting from vertex" starting-vertex-id "on" digraph)

  (def starting-vertex {starting-vertex-id (get digraph starting-vertex-id)})

  (loop [vertices-to-visit (conj {} starting-vertex)
         visited-vertices {}
         distances (assoc-in {} [starting-vertex-id] 0)
         de-infinitizer 0]

    (if (or (empty? vertices-to-visit) (> de-infinitizer 100))

      distances

      (do
        (let [vertex-visiting (first vertices-to-visit)
              visiting-distance (get distances (first vertex-visiting))]

          (if (contains? visited-vertices (first vertex-visiting))

            (recur (rest vertices-to-visit)
                   visited-vertices
                   distances
                   (inc de-infinitizer))

            (do

              (def connected-vertices (get-vertices-for-indeces digraph (val vertex-visiting)))
              (def connected-vertices-without-distance
                (into {}
                      (filter #(not (contains? distances (key %))) connected-vertices)))

              ; add distances for newly connected vertices
              (def updated-distances
                (reduce (fn [distances vertex]
                          (assoc-in distances [(first vertex)] (inc visiting-distance)))
                        distances connected-vertices-without-distance))

              (recur (merge (into {} (rest vertices-to-visit))
                            (get-vertices-for-indeces digraph (val vertex-visiting)))
                     (conj visited-vertices vertex-visiting)
                     updated-distances
                     (inc de-infinitizer)))))))))


(shortest-distances test-digraph 4)



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


