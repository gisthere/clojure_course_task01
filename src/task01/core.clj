(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))

; так-как вектор по сути является деревом, то применим к нему DFS
(defn links[arg]
  ; обход смежных вершин (элементов вектора)
  ; acc аккумулирует значения, которые затем вернёт рекурсия
  (loop [b arg acc []]
  ; условие выхода из цикла и рекурсии 
   (if (and (coll? b) (not (empty? b)))
     ; если в векторе найден элемент удовлетворяющий первому условию, то извлекаем из вектора ссылку
     (if (some #(= (:class %) "r") b)
       (conj acc (some (fn f1 [arg] (when (coll? arg) (some #(:href %) arg))) b))
       ; иначе переходим вглубь
       (recur (next b) (into acc (links (first b)))))
     acc)))

(defn get-links []
" 1) Find all elements containing {:class \"r\"}.

Example:
[:h3 {:class \"r\"} [:a {:shape \"rect\", :class \"l\",
                         :href \"https://github.com/clojure/clojure\",
                         :onmousedown \"return rwt(this,'','','','4','AFQjCNFlSngH8Q4cB8TMqb710dD6ZkDSJg','','0CFYQFjAD','','',event)\"}
                     [:em {} \"clojure\"] \"/\" [:em {} \"clojure\"] \" · GitHub\"]]

   2) Extract href from the element :a.

The link from the example above is 'https://github.com/clojure/clojure'.

  3) Return vector of all 10 links.

Example: ['https://github.com/clojure/clojure', 'http://clojure.com/', . . .]
"
  (let [data (parse "clojure_google.html")]
    (links data)))

(defn -main []
  (println (str "Found " (count (get-links)) " links!")))



