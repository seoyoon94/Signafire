(ns signafire.core
  (:gen-class))

(defn to-hours
  "Transforms a map containing project information into hours.
  Hours is defaulted to 0 if the hours key does not exist."
  [{hours :hours, :or {hours 0}}]
  hours)

(defn is-capex-project?
  "Returns true if the project is a capex project.
  False otherwise."
  [{capex :capex, :or {capex false}}]
  (true? capex))

(defn filter-capex-projects
  "Given a collection of project information, filter and
  return a collection of capex projects."
  [project-infos]
  (filter is-capex-project? project-infos))

(defn extract-valid-hours
  "Extracts hours from valid projects given a list of project 
  information defined by `project-infos` and a filter strategy
  defined by `filter-strategy`."
  [filter-strategy project-infos]
  (let [filtered-projects (filter-strategy project-infos)]
    (map to-hours filtered-projects)))

(defn determine-filter-strategy
  "Determines which filter strategy to use to calculate total
  project hours. Currently filters only according to the capex flag.
  Could be configurable depending on requirements."
  []
  filter-capex-projects)

(defn total-hours
  "Calculates the total amount of hours of all projects given
  a predetermined filter strategy."
  [projects]
  (let [valid-hours (extract-valid-hours (determine-filter-strategy) (vals projects))]
    (reduce + 0 valid-hours)))

(defn create-fixture
  []
  {:project1 {:hours  7
              :capex true},
   :project2 {:hours 3,
              :capex false},
   :project3 {:hours 6,
              :capex true}})

(defn -main []
  (println (str "The answer is: " (total-hours (create-fixture)))))