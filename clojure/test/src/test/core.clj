(ns test.core)

(defn f-recur [n]
  (cond (< n 3) n
        :else (+ (f-recur (- n 1))
                 (* 2 (f-recur (- n 2)))
                 (* 3 (f-recur (- n 3))))))

(defn f-iter [n]
  (defn f [a b c] (+ a (* 2 b) (* 3 c)))
  (defn f-internal [a b c i]
    (cond (< n 3) n
          :else (cond (> i n) a
                      :else (f-internal (f a b c) a b (+ i 1)))))
  (f-internal 2 1 0 3))


(defn pascal [row col]
  (cond (or
          (= col 0)
          (= row col)) 1
        :else (+
                (pascal (- row 1) (- col 1))
                (pascal (- row 1) col))))


(defn pascal-triangle [n]
  (defn pascal-row [row col row-acum]
    (cond (> col row) (str row-acum "|")
          :else (pascal-row row
                            (+ col 1)
                            (str row-acum "|"
                                 (pascal row col)))))
  (defn pascal-iter [row pascal-acum]
    (cond (> row n) pascal-acum
          :else (pascal-iter (+ row 1)
                             (str
                               pascal-acum
                               "\n"
                               (pascal-row row 0 "")))))
  (pascal-iter 0 ""))


(print (pascal-triangle 10))