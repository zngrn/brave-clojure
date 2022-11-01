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

;; take, drop, take-while, drop-while
(take 3 '(1 2 3 4 5 6 7)) ;; => (1 2 3)
(drop 2 [1 2 3 4 5]) ;; => (3 4 5)

(def monthly-analysis
  [{:month 1 :games 8 :goals 1 :assists 2 :yellow 2 :red 0}
   {:month 2 :games 9 :goals 3 :assists 4 :yellow 2 :red 0}
   {:month 3 :games 6 :goals 2 :assists 5 :yellow 1 :red 0}
   {:month 4 :games 8 :goals 3 :assists 3 :yellow 1 :red 0}
   {:month 5 :games 8 :goals 2 :assists 4 :yellow 3 :red 0}
   {:month 6 :games 5 :goals 0 :assists 4 :yellow 1 :red 0}])

(take-while #(< (:month %) 4) monthly-analysis) ;; returns the first quarter stats
(drop-while #(< (:month %) 4) monthly-analysis) ;; returns the second quarter stats

(def aggregate-stats #(reduce + %))

(defn total-games-played
  [numbers]
  (aggregate-stats (map #(% :games) numbers)))

(defn total-goals-scored
  [numbers]
  (aggregate-stats (map #(% :assists) numbers)))

(defn total-assists-given
  [numbers]
  (aggregate-stats (map #(% :goals) numbers)))

(total-games-played monthly-analysis) ;; => 44
(total-goals-scored monthly-analysis) ;; => 22
(total-assists-given monthly-analysis) ;; => 11

;; further refining this to DRY friendly
(defn total-stats
  [numbers key]
  (aggregate-stats (map #(% key) numbers)))

(total-stats monthly-analysis :goals) ;; => 11

;; filter
(filter #(>= (:yellow %) 3) monthly-analysis) ;; => ({:month 5, :games 8, :goals 2, :assists 4, :yellow 3, :red 0})
(comment
  "filter can end up going over all the data. 
  Use wisely for large data-sets.")

;; some
(comment
  "Test condition for the first truthy value.")
(some #(> (:red %) 1) monthly-analysis) ;; => nil
(some #(> (:yellow %) 1) monthly-analysis) ;; => true

;; sort & sort-by
(sort '(-123 123 8 0 12)) ;; => (-123 0 8 12 123)
(sort-by count ["a" "a" "aa" "bb" "ccc"]) ;; => ("a" "a" "aa" "bb" "ccc")

;; concat
(concat [1 2 3] [-1 -2 0]) ;; => (1 2 3 -1 -2 0)

;; lazy seqs
(comment 
  "lazy seqs are seqs whose members aren't computed
   until they are accessed. Computing them is called
   'realising'. Deferring this execution is for efficiency.")

(def first-team-players
  {0 {:fit? true :number 1 :available? true :name "foo"}
   1 {:fit? false :number 4 :available? false :name "bar"}
   2 {:fit? true :number 7 :available? true :name "baz"}
   3 {:fit? true :number 10 :available? false :name "foz"}})

(defn player-details
  [number]
  (Thread/sleep 1000)
  (get first-team-players number))

(defn fit?
  [record]
  (and (:fit? record)
       (:available? record)
  record))

(defn identify-player
  [numbers]
  (first (filter fit?
                 (map player-details numbers))))

(time (player-details 0))
;; => "Elapsed time: 1001.178908 msecs"
;; => {:fit? true, :number 1, :available? true :name "foo"}

;; infinite sequences
(concat ["nah" "nah" "nah"] (take 2 (repeat "na na na nah!")) ["Hey Jude!"])
;; => ("nah" "nah" "nah" "na na na nah!" "na na na nah!" "Hey Jude!")

(take 5 (repeatedly (fn [] (rand-int 10)))) 
;; will print a random number 5 times within the range of 0..10

(comment
  "collection functions to play around with the entire
   data structure instead of focusing on an individual
   seq."
  )
;; into
(map identity {:name "foo"}) ;; => ([:name "foo"])
(into {} (map identity {:name "foo"})) ;; => {:name "foo"}
;; this converts the ds back to a map.
;; similarly
(map identity [:name :age :address]) ;; => (:name :age :address)
(into [] (map identity [:name :age :address])) ;; => [:name :age :address]
;; into's first arguement needn't be empty
(into #{:a :b} (map identity [:a :b :c :d])) ;; => #{:c :d :b :a}

;; conj
(conj [0] [1]) ;; => [0 [1]]
;; or to replicate into's functionality
(conj [0] 1) ;; => [0 1]
;; or
(conj [-1] 0 1 2 3 :foo) ;; => [-1 0 2 3 :foo]

(comment
  "Functions that take in a function and return a function.")
;; apply
(comment
  "apply explodes a seq to be passed in to a function."
  )
(max -1 2 7) ;; => 7
(max [0 1 2]) ;; => [0 1 2]
(apply max [0 1 2]) ;; => 2

;; partial
(comment
  "partial takes a function and any no of arguements to return a function.
   When calling the new function, it calls the original function with the
   original args along with the new args."
  )
(def add3 (partial + 3))
(add3 4) ;; => 7

(def add-details
  (partial conj [:name :age :role :employee_id]))
(add-details :manager :doj) ;; => [:name :age :role :employee_id :manager :doj]

;; a more succinct example
(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :error (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))
(warn "watch out!") ;; => "watch out!"
(def error (partial lousy-logger :error))
(error "undefined exception!") ;; => "UNDEFINED EXCEPTION!"

;; complement
(def not-empty? (complement empty?))
(not-empty? [1]) ;; => true
(not-empty? '()) ;; => false
