(ns getting-started.core
  (:gen-class))

(defn hello
  "hello function when there are not params it prints clojure,
  otherwise it prints the param"
  ([] (hello "clojure"))
  ([name] (str "hello " name)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (hello)))
