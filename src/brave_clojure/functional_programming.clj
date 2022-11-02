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


