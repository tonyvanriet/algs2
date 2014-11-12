(ns word-net.digraph
  (:gen-class))


(defn new-connections
  "Returns a new empty set of vertex identifiers intended for use with a vertex to represent
  it's connecting vertices.
  The idea is for this to be the only code that knows that connections are a set, and have
  them used as abstract collections everywhere else in code."
  []
  #{})


(defn new-edges
  "Returns a new empty set of edges.
  The idea is for this to be the only code that knows edges are a set, and have
  them used as abstract collections everywhere else in code."
  []
  #{}
  )

(defn add-edge

  ([v w digraph]

   (let [connections (get digraph v)]

     (def digraph-with-new-edge
       (assoc-in digraph
                 [v]
                 (if (nil? connections)
                   (conj (new-connections) w)
                   (conj connections w))))

     (if (contains? digraph-with-new-edge w)
       digraph-with-new-edge
       (assoc-in digraph-with-new-edge
                 [w]
                 (new-connections)))))


  ([[v w] digraph]
   (add-edge v w digraph)))



(defn num-vertices
  [digraph]
  (count digraph))

(defn num-edges
  [digraph]
  (reduce + (map count (vals digraph))))


(defn build-new-digraph
  ([]
   {})
  ([edges]
   (loop [edges edges
          digraph (build-new-digraph)]
     (if (nil? (first edges))
       digraph
       (recur (rest edges)
              (add-edge (first edges) digraph))))))


(build-new-digraph [[1 2] [3 4] [3 5]])


(defn digraph->edges

  "Converts the digraph into a set of edge connections."

  [digraph]

  (let [vertices digraph]

    ; ugly duplicate nil check here so an empty digraph doesn't cause an ex
    ; when used in the loop bindings.
    (if (nil? (first vertices))

      (new-edges)

      (loop [vertices vertices
             connections (val (first vertices))
             edges (new-edges)]

        (if (nil? (first vertices))

          edges

          (if (nil? (first connections))

            (recur (rest vertices)
                   (if (nil? (first (rest vertices)))
                     nil
                     (val (first (rest vertices))))
                   edges)

            (recur vertices
                   (rest connections)
                   (conj edges [(key (first vertices)) (first connections)]))))))))


(digraph->edges (build-new-digraph))

(digraph->edges (build-new-digraph [[1 2] [3 4] [3 5]]))


(defn reverse-edge
  [edge]
  (reverse edge))


(defn reverse-digraph
  "Returns a digraph with the connections reversed."
  [digraph]
  (build-new-digraph (map reverse-edge (digraph->edges digraph))))


(defn print-digraph
  [digraph]
  (println (num-vertices digraph) "vertices," (num-edges digraph) "edges.")
  (doall (map println digraph))
  nil)





