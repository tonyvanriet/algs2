(ns word-net.core-test
  (:require [clojure.test :refer :all])
  (:require word-net.core)
  (:require [word-net.digraph :as digraph]))



; digraph tests

(deftest new-connections-returns-empty-set
  (testing "new-connections returns an empty set"
    (is (= (digraph/new-connections) #{}))))


(deftest add-edge
  (testing "add-edge adds an edge"
    (is (= (digraph/add-edge 1 2 (digraph/build-new-digraph))
           {1 #{2}}))))


(deftest build-new-digraph-returns-empty-map
  (testing "build-new-digraph with no edges returns an empty map"
    (is (= (digraph/build-new-digraph)))))


(deftest build-new-digraph-with-some-edges
  (testing "build-new-digraph returns correct digraph"
    (is (= (digraph/build-new-digraph
            [[1 2] [3 5]]) {1 #{2}
                            3 #{5}}))))


(deftest num-vertices
  (testing "num-vertices"
    (is (= 3 (digraph/num-vertices
              (digraph/build-new-digraph [[1 2][3 4][5 6][5 7]]))))))


(deftest num-edges
  (testing "num-edges"
    (is (= 4 (digraph/num-edges (digraph/build-new-digraph [[1 2][3 4][5 6][5 7]]))))))

