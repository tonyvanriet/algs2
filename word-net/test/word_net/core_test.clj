(ns word-net.core-test
  (:require [clojure.test :refer :all])
  (:require [word-net.core :refer :all])
  (:require [word-net.digraph :refer :all])
  (:require [word-net.sap :refer [length ancestor]]))



(deftest digraph-tests

  (testing "new-connections returns an empty set"
    (is (= (new-connections) #{})))

  (testing "add-edge adds an edge"
    (is (= (add-edge 1 2 (build-new-digraph))
           {1 #{2}, 2 #{}})))

  (testing "build-new-digraph with no edges returns an empty map"
    (is (= (build-new-digraph) {})))

  (testing "build-new-digraph returns correct digraph"
    (is (= (build-new-digraph
            [[1 2] [3 5]]) {1 #{2}, 3 #{5}, 2 #{}, 5 #{}})))

  (testing "num-vertices"
    (is (= 7 (num-vertices
              (build-new-digraph [[1 2][3 4][5 6][5 7]]))))))

  (testing "num-edges"
    (is (= 4 (num-edges (build-new-digraph [[1 2][3 4][5 6][5 7]])))))

  (testing "digraph-edges returns edges"
    (def edges #{[1 2] [3 4] [3 5]})
    (is (= (digraph->edges (build-new-digraph edges)))))

  (testing "reverse-edge reverses an edge"
    (is (= (reverse-edge [1 2]) [2 1])))

  (testing "reverse-digraph returns reversed digraph"
    (is (= (reverse-digraph {1 #{2 3}, 2 #{}, 3 #{}, 4 #{5 6}, 5 #{}, 6 #{}})
           {1 #{}, 2 #{1}, 3 #{1}, 4 #{}, 5 #{4}, 6 #{4}})))



(def test-digraph-edges [[12 10] [11 10] [10 5] [9 5] [8 3]
                         [7 3] [3 1] [4 1] [5 1] [1 0] [2 0]])

(def test-digraph (build-new-digraph test-digraph-edges))

; the shortest ancestral path between 3 and 11 has length 4 (with common ancestor 1).

(deftest sap-tests

  (testing "sap length"
    (is (= 4 (length 3 11 test-digraph))))

  (testing "closest ancestor"
    (is (= 1 (ancestor 3 11 test-digraph))))

  (testing "sap length 2"
    (is (= 3 (length 2 5 test-digraph))))

  (testing "closest ancestor 2"
    (is (= 0 (ancestor 2 5 test-digraph)))))
