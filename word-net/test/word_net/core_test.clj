(ns word-net.core-test
  (:require [clojure.test :refer :all])
  (:require word-net.core)
  (:require word-net.digraph))



; digraph tests

(deftest new-connections-returns-empty-set
  (testing "new-connections returns an empty set"
    (is (= (word-net.digraph/new-connections) #{}))))


(deftest add-edge
  (testing "add-edge adds an edge"
    (is (= (word-net.digraph/add-edge 1 2 (word-net.digraph/build-new-digraph))
           {1 #{2}}))))


(deftest build-new-digraph-with-some-edges
  (testing "build-new-digraph returns correct digraph"
    (is (= (word-net.digraph/build-new-digraph
            [[1 2] [3 5]]) {1 #{2}
                            3 #{5}}))))


(deftest num-vertices
  (testing "num-vertices"
    (is (= 3 (word-net.digraph/num-vertices
              (word-net.digraph/build-new-digraph [[1 2][3 4][5 6][5 7]]))))))


(deftest num-edges
  (testing "num-vertices"
    (is (= 4 (word-net.digraph/num-edges (word-net.digraph/build-new-digraph [[1 2][3 4][5 6][5 7]]))))))
