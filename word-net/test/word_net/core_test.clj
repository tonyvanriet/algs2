(ns word-net.core-test
  (:require [clojure.test :refer :all])
  (:require [word-net.core :refer :all])
  (:require [word-net.digraph :as dig])
  (:require [word-net.sap :as sap])
  (:require [word-net.data :as data])
  (:require [word-net.file-io :as fio]))



(deftest digraph-tests

  (testing "new-connections returns an empty set"
    (is (= (dig/new-connections) #{})))

  (testing "add-edge adds an edge"
    (is (= (dig/add-edge 1 2 (dig/build-new-digraph))
           {1 #{2}, 2 #{}})))

  (testing "build-new-digraph with no edges returns an empty map"
    (is (= (dig/build-new-digraph) {})))

  (testing "build-new-digraph returns correct digraph"
    (is (= (dig/build-new-digraph
            [[1 2] [3 5]]) {1 #{2}, 3 #{5}, 2 #{}, 5 #{}})))

  (testing "num-vertices"
    (is (= 7 (dig/num-vertices
              (dig/build-new-digraph [[1 2][3 4][5 6][5 7]]))))))

  (testing "num-edges"
    (is (= 4 (dig/num-edges (dig/build-new-digraph [[1 2][3 4][5 6][5 7]])))))

  (testing "digraph-edges returns edges"
    (def edges #{[1 2] [3 4] [3 5]})
    (is (= (dig/digraph->edges (dig/build-new-digraph edges)))))

  (testing "reverse-edge reverses an edge"
    (is (= (dig/reverse-edge [1 2]) [2 1])))

  (testing "reverse-digraph returns reversed digraph"
    (is (= (dig/reverse-digraph {1 #{2 3}, 2 #{}, 3 #{}, 4 #{5 6}, 5 #{}, 6 #{}})
           {1 #{}, 2 #{1}, 3 #{1}, 4 #{}, 5 #{4}, 6 #{4}})))



(def test-digraph-edges [[12 10] [11 10] [10 5] [9 5] [8 3]
                         [7 3] [3 1] [4 1] [5 1] [1 0] [2 0]])

(def test-digraph (dig/build-new-digraph test-digraph-edges))

; the shortest ancestral path between 3 and 11 has length 4 (with common ancestor 1).

(deftest sap-tests

  (testing "sap length"
    (is (= 4 (sap/length 3 11 test-digraph))))

  (testing "closest ancestor"
    (is (= 1 (sap/ancestor 3 11 test-digraph))))

  (testing "sap length 2"
    (is (= 3 (sap/length 2 5 test-digraph))))

  (testing "closest ancestor 2"
    (is (= 0 (sap/ancestor 2 5 test-digraph)))))



(deftest word-net-search-tests

  (testing "ancestor search in synsets and hypernyms"
    (def ancestor-synset
      (data/sap "black-backed_gull"
                "Nathaniel_Bowditch"
                (fio/get-synsets-from-file (str fio/file-path "synsets.txt"))
                (fio/get-hypernym-digraph-from-file (str fio/file-path "hypernyms.txt")))))
  (is (= "organism" (first (:nouns ancestor-synset))))

  ; the edges in the larger hypernym files just seem wrong,
  ; as if they're refering to a different synsets file
  (testing "ancestor search in synsets and hypernyms100k"
    (def ancestor-synset
      (data/sap "'hood"
                "nymphet"
                (fio/get-synsets-from-file (str fio/file-path "synsets.txt"))
                (fio/get-hypernym-digraph-from-file (str fio/file-path "hypernyms100k.txt")))))
  (is (= "Phytophthora_infestans" (first (:nouns ancestor-synset)))))


