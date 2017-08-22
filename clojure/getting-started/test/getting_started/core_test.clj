(ns getting-started.core-test
  (:require [clojure.test :refer :all]
            [getting-started.core :refer :all]))

(deftest test-hello
  (testing "test hello without params"
    (is (= "hello clojure" (hello)))
    (is (= "hello hello clojure" (hello (hello)))))
  (testing "test hello with params"
    (is (= "hello felipe" (hello "felipe")))))

(deftest test-other
  (testing "other thing"
           (is (= 5 5))))

