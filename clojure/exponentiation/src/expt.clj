(ns expt
  (:use clojure.test))

(defn expt [b n]
  (cond (= n 0) 1
        :else (*
                b
                (expt b (- n 1)))))


(deftest test-exp
  (is (= 2 (expt 2 1)))
  (is (= 4 (expt 2 2)))
  (is (= 8 (expt 2 3)))
  (is (= 16 (expt 2 4)))
  (is (= 32 (expt 2 5))))

(defn expt-iter
  ([b n] (expt-iter b n 1))
  ([b n product]
   (cond (= n 0) product
         :else (expt-iter b (- n 1) (* b product)))))

(deftest test-exp-iter
  (is (= 2 (expt-iter 2 1)))
  (is (= 4 (expt-iter 2 2)))
  (is (= 8 (expt-iter 2 3)))
  (is (= 16 (expt-iter 2 4)))
  (is (= 32 (expt-iter 2 5))))

(defn square [n] (* n n))

(defn fast-expt [b n]
  (cond
    (= n 0) 1
    (even? n) (square
                (fast-expt b (/ n 2)))
    :else (*
            b
            (fast-expt b (- n 1)))))

(deftest test-fast-exp
  (is (= 2 (fast-expt 2 1)))
  (is (= 4 (fast-expt 2 2)))
  (is (= 8 (fast-expt 2 3)))
  (is (= 16 (fast-expt 2 4)))
  (is (= 32 (fast-expt 2 5))))

(defn fast-expt-iter
  ([b n] (fast-expt-iter b n 1))
  ([b n a]
   (cond (= n 0) a
         (even? n) (fast-expt-iter (square b) (/ n 2) a)
         :else (fast-expt-iter b (- n 1) (* b a)))))

(deftest test-fast-expt-iter
  (testing "n = 0 b = 2 should be 1"
    (is (= 1 (fast-expt-iter 2 0))))
  (testing "n = 1 b = 2 should be 2"
    (is (= 2 (fast-expt-iter 2 1))))
  (testing "n = 2 b = 2 should be 4"
    (is (= 4 (fast-expt-iter 2 2))))
  (testing "n = 3 b = 2 should be 8"
    (is (= 8 (fast-expt-iter 2 3))))
  (is (= 16 (fast-expt-iter 2 4)))
  (is (= 32 (fast-expt-iter 2 5)))
  (is (= (expt 3 3) (fast-expt-iter 3 3))))

(defn * [a b]
  (cond (= b 0) 0
        :else (+ a (* a (- b 1)))))

(deftest test-*
  (testing "a = 0 b = 0 is 0"
    (is (= 0 (* 0 0))))
  (testing "a = 1 b = 0 is 0"
    (is (= 0 (* 1 0))))
  (testing "a = 1 b = 1 is 1"
    (is (= 1 (* 1 1))))
  (testing "a = 10 b = 15 is 150"
    (is (= 150 (* 10 15)))))

(defn double [a] (+ a a))
(defn halve [b] (/ b 2))

(defn iter*
  ([a b] (iter* a b 0))
  ([a b counter]
   (cond (= b 0) counter
         (even? b) (iter* (double a) (halve b) counter)
         :else (iter* a (- b 1) (+ a counter)))))


(deftest test-*-iter
  (testing "any number * by 0 should be 0"
    (is (= 0 (iter* 10 0))))
  (testing "any number * by 1 should be same number"
    (is (= 6 (iter* 6 1))))
  (testing "5 * 2 should be 10"
    (is (= 10 (iter* 5 2))))
  (testing "150 * 87 should be 13050"
    (is (= 13050 (iter* 150 87)))))

(run-tests)