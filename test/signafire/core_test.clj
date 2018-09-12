(ns signafire.core-test
  (:require [clojure.test :refer :all]
            [signafire.core :refer :all]))

(deftest to-hours-test
  (testing "test valid maps to hours"
    (is (= (to-hours {:hours 5}) 5)))
  (testing "test maps with no hours key defaults to 0"
    (is (= (to-hours {:prop "Test"}) 0))))

(deftest is-capex-project?-test
  (testing "test return true for capex projects"
    (is (= (is-capex-project? {:capex true}) true)))
  (testing "test return false for non-capex projects"
    (is (= (is-capex-project? {:capex false}) false))
    (is (= (is-capex-project? {:capex "false"}) false))
    (is (= (is-capex-project? {:capex "true"}) false))))

(deftest extract-valid-hours-test
  (testing "test return vector of project hours filtered by a strategy"
    (let [filter-strategy (fn [projects] projects)
          projects `({:hours 3 :capex false} {:hours 5 :capex true})]
      (is (= (extract-valid-hours filter-strategy projects) `(3 5))))))

(deftest total-hours-test
  (testing "test return total number of hours for the default filter strategy"
    (let [projects {:project1 {:hours 5 :capex false}
                    :project2 {:hours 3 :capex true}
                    :project3 {:hours 4 :capex true}}]
      (is (= (total-hours projects) 7)))))