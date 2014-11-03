(ns word-net.core-test
  (:require [clojure.test :refer :all])
  (:require [word-net.core :refer :all])
  (:require [word-net.digraph :refer :all]))



; digraph tests

(deftest new-connections-returns-empty-set
  (testing "new-connections returns an empty set"
    (is (= (new-connections) #{}))))


(deftest add-edge-adds-an-edge
  (testing "add-edge adds an edge"
    (is (= (add-edge 1 2 (build-new-digraph))
           {1 #{2}}))))


(deftest build-new-digraph-returns-empty-map
  (testing "build-new-digraph with no edges returns an empty map"
    (is (= (build-new-digraph) {}))))


(deftest build-new-digraph-with-some-edges
  (testing "build-new-digraph returns correct digraph"
    (is (= (build-new-digraph
            [[1 2] [3 5]]) {1 #{2} 3 #{5}}))))


(deftest num-vertices-returns-correct-num-vertices
  (testing "num-vertices"
    (is (= 3 (num-vertices
              (build-new-digraph [[1 2][3 4][5 6][5 7]]))))))


(deftest num-edges-returns-correct-num-edges
  (testing "num-edges"
    (is (= 4 (num-edges (build-new-digraph [[1 2][3 4][5 6][5 7]]))))))


(deftest digraph->edges-returns-edges
  (testing "digraph-edges returns edges"
    (def edges #{[1 2] [3 4] [3 5]})
    (is (= (digraph->edges (build-new-digraph edges))))))


(deftest reverse-edge-reverses-edge
  (testing "reverse-edge reverses an edge"
    (is (= (reverse-edge [1 2]) [2 1]))))


(deftest reverse-digraph-returns-reversed-digraph
  (testing "reverse-digraph returns reversed digraph"
    (is (= (reverse-digraph {1 #{2 3} 4 #{5 6}})
           {2 #{1} 3 #{1} 5 #{4} 6 #{4}}))))

