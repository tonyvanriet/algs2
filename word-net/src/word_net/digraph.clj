(ns word-net.digraph
  (:gen-class))


(defn new-connections
  []
  #{})


(defn add-edge

  ([v w digraph]

   (let [connections (get digraph v)]
     (assoc-in digraph
               [v]
               (if (nil? connections)
                 (conj (new-connections) w)
                 (conj connections w)))))

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

  "Converts the digraph into an array of edge connections."

  [digraph]

  (let [vertices digraph]

    ; ugly duplicate nil check here so an empty digraph doesn't cause an ex
    ; when used in the loop bindings.
    (if (nil? (first vertices))

      []

      (loop [vertices vertices
             connections (val (first vertices))
             edges []]

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


(defn reverse-digraph
  [digraph]
  )
