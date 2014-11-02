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
       (recur (rest edges) (add-edge (first edges) digraph))))))

