(ns brave-clojure.core-functions)

(comment
  "seq is the underlying mechanism of transforming
  data structures into sequences which can further
  be used to call functions like map, first, cons, conj
  rest etc.
  This is the reason vectors, maps, sets are returned
  as lists post transformation of function applies.")
(seq '(1 2 3)) ;; => (1 2 3)
(seq [1 2 3]) ;; => (1 2 3)
(seq {:name "foo" :age 42}) ;; => ([:name "foo"] [:age 42])

(comment
  "Note how seq on maps treats it like a list of vectors.")

(comment
  "Converting seq back to a map with into.")
(into {} (seq {:a 12 :b 13 :d 17})) ;; => {:a 12 :b 13 d: 17}

;; Sequence functions
;; map
(map inc '(1 2 3)) ;; => (2 3 4)
(map str ["a" "b"] ["A" "B"]) ;; => ("aA" "bB")

(def touches-per-90 [20 25 14 30])
(def assists-per-90 [0 1 1 2 0])
(defn unify-player-data
  [touches assists]
  {:touches touches
   :assists assists})
(map unify-player-data touches-per-90 assists-per-90)
;; => ({:touches 20, :assists 0} {:touches 25, :assists 1} {:touches 14, :assists 1} {:touches 30, :assists 2})

(def sum-of #(reduce + %))
(def avg-of #(/ (sum-of %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum-of avg-of count]))
(stats [0 1 0 0 2 0 1 0 0 0 2 3]) ;; => (9 3/4 12)

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
;; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")

;; reduce
(reduce + [1 2 3 4 5]) ;; => 15
(reduce conj #{} [:a :b :b :c :d :f "foo" "bar"]) ;; => #{"foo" "bar" :c :b :d :f :a}
(reduce conj [1 2 3 4] [4 5 5 7 9]) ;; => [1 2 3 4 4 5 5 7 9]
(comment
  "A key takeaway from reduce is it helps
   derive a new value from a seq data structure.")

