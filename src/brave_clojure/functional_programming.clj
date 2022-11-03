(ns brave-clojure.functional-programming)

(comment
  "Pure Functions
   -> returns the same result when given the same args.
   This is called referential transparency.
   -> doesn't create side effects
   
   This makes isolated functions in programs which do
   not impact other parts. Also, consistency.")

;; referential transparency
(+ 1 12)
(defn greetings
  [name]
  (str "Hello! " name))
(greetings "foo")
(greetings "bar")
;; here, greetings is also referentially transparent
;; because the function relies of an immutable string.
(defn raise-logic
  []
  (if (> (rand) 0.5)
    "You're getting a raise!"
    "No raise for you."))
(raise-logic)
(comment
  "this will always return either of the statements
   with the same arguements due to the usage of rand.
   similarly reading from a file or using println will
   not be a pure function.
   ")

;; pure fucntions sans side effects
(comment
  "Side effects are inevitable and potentially harmful.
   However, when calling functions without side effects,
   you only consider the relationship between input & output.
   Objects being passed around, it's attributes change, and
   traceability becomes a liability. Mostly, state handling.
   Since core data structures in clojure are immutable, side
   effects are mitigated.")

(comment
  "Functional alternative to mutation is recursion.")

;; function composition over mutation
(require '[clojure.string :as s])
(defn clean-text
  [text]
  (s/replace (s/trim text) #"lol" "*laugh out loud!*"))
(clean-text "This is hilarious! lol      ") ;; => "This is hilarious! *laugh out loud!*"

(comment
  "combining functions like trim and replace where the output
   of one function is used as the arguement of another function
   is called function composition."
  )

(comment
  "In OOP, the function of classes is to protect the change
   of state of mutation and prevent unwanted modification
   of objects, specifically private data. This isn't needed
   with immutable data structures.
   Additionally, class functions are tightly bound and limit
   the use whereas functions here can be applied to any argument
   of a similar data type. This decouples the function and 
   programs to a small set of abstractions.")

;; comp
;; creating a new function from the composition of
;; any number of functions.
((comp inc *) 2 5) ;; => 11
;; A more succinct example of comp
(def dota-character-stats
  {:name "Ember Spirit"
   :attributes {
                :intelligence 7
                :agility 9
                :strength 8}})
(def attribute-intelligence (comp :intelligence :attributes))
(def attribute-agility (comp :agility :attributes))
(def attribute-strength (comp :strength :attributes))

(attribute-intelligence dota-character-stats) ;; => 7
(attribute-agility dota-character-stats) ;; => 9
(attribute-strength dota-character-stats) ;; => 8
