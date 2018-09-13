;;;;;;;;;;;;;;;;;;;;;;;
;; First code snippet
;;;;;;;;;;;;;;;;;;;;;;;
(map
 #(cond (divides? 15 %) "FizzBuzz"
        (divides? 3 %)  "Fizz"
        (divides? 5 %)  "Buzz"
        :else %) (range 1 101))

;; To document and make this function more easily testable, I would first
;; separate out the mapping function to its own function and keep the original
;; structure.
;; For example:

(defn to-fizz-buzz
  "Maps a number to Fizz, Buzz, or both depending if the dividend is evenly
    divisible by certain divisors. If the input number is not evenly divisible,
    the original number is not mapped. Input numbers will be mapped as follow:
    1) If number is evenly divisible by 3: Fizz
    2) If number is evenly divisible by 5: Buzz
    3) If number is evenly divisible by 15: FizzBuzz
    4) If none of the above: Original number is returned"
  [number]
  (cond (divides? 15 number) "FizzBuzz"
        (divides? 3 number)  "Fizz"
        (divides? 5 number)  "Buzz"
        :else number))

(map to-fizz-buzz (range 1 101))

;; In this case to-fizz-buzz can be unit tested by providing numbers and edge
;; cases without depending on the map functionality. Below is the pseudocode
;; unit testing the to-fizz-buzz function.

;; Test to-fizz-buzz
;;   Assert (to-fizz-buzz 3) is Fizz
;;   Assert (to-fizz-buzz 5) is Buzz
;;   Assert (to-fizz-buzz 15) is FizzBuzz
;;   Assert (to-fizz-buzz 30) is FizzBuzz
;;   Assert (to-fizz-buzz 4) is 4

;; Other than the to-fizz-buzz unit test, an integration test can be written
;; which covers the mapping functionality for a collection of numbers.

;; Test mapping
;;    Define results as (map to-fizz-buzz (range 1 101))
;;    Assert (nth results 0) is 1
;;    Assert (nth results 2) is Fizz
;;    Assert (nth results 4) is Buzz
;;    Assert (nth results 14) is FizzBuzz

;;;;;;;;;;;;;;;;;;;;;;;
;; Second code snippet
;;;;;;;;;;;;;;;;;;;;;;;

(defresource create-label
  [database project-id inv-id user-id message-ids name doi]
  resource/base-resource
  :allowed-methods [:post]
  :handle-ok (fn [_]
               (let [message-ids (json/parse-string message-ids)]

                 {:label_id (db-label/create-label
                             (:connection database)
                             project-id
                             inv-id
                             user-id
                             message-ids
                             name
                             doi)})))

;; Since the parameterized resource is being served as a REST API, I would
;; test to see that only POST HTTP methods work by mocking a request to the
;; resource endpoint and checking to see if a 405 status code is returned.
;; Additionally, I would test the POST method using a mock request
;; on a valid route to check if the HTTP header returns a 200 status code 
;; along with the requested resource in the response body. This assumes
;; that json/parse-string and db-label/create-label are functions that already
;; have existing unit tests.

;; To document this code snippet, I would add a comment explaining each
;; parameter for the function, which HTTP methods are allowed for the
;; resource, and explain the results of a successful POST request for the
;; resource.