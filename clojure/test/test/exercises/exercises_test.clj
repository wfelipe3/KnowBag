(ns exercises.exercises-test
  (:use (test.core))
  (:require [clojure.test :refer :all]
            [test.core :refer :all]))

(deftest test-f-recur
  (testing "recursive function"
    (is (= 25
           (f-recur 5)))))

(deftest test-f-iter
  (testing "n = 0 should be 0"
    (is (= 0
           (f-iter 0))))
  (testing "n = 1 should be 1"
    (is (= 1
           (f-iter 1))))
  (testing "n = 2 should be 2"
    (is (= 1
           (f-iter 1))))
  (testing "n = 3 should be 4"
    (is (= (f-recur 3)
           (f-iter 3))))
  (testing "n = 4 should be 11"
    (is (= (f-recur 4)
           (f-iter 4))))
  (testing "n = 5 should be 25"
    (is (= (f-recur 5)
           (f-iter 5))))
  (testing "n = 20 should be 10771211"
    (is (= (f-recur 20)
           (f-iter 20)))))

(deftest test-pascal-triangle
  (testing "row = 0 and col = 0 return 1"
    (is (= 1 (pascal 0 0))))
  (testing "row = 1 and col = 0 return 1"
    (is (= 1 (pascal 1 0))))
  (testing "row = 1 and col = 1 return 1"
    (is (= 1 (pascal 1 1))))
  (testing "row = 2 and col = 0 return 1"
    (is (= 1 (pascal 1 1))))
  (testing "row = 2 and col = 2 return 1"
    (is (= 1 (pascal 1 1))))
  (testing "row = 2 and col = 1 return 2"
    (is (= 2 (pascal 2 1))))
  (testing "row = 4 and col = 2 return 6"
    (is (= 6 (pascal 4 2))))
  (testing "row = 4 and col = 1 return 4"
    (is (= 4 (pascal 4 1))))
  (testing "row = 4 and col = 3 return 4"
    (is (= 4 (pascal 4 3)))))


(run-tests)


