(defproject
  ;; Don't forget VERSION in src/ruby/pacer-parallel/version.rb
  pacer.parallel "0.1.0"
  :description "Leveraging core.async in Pacer"
  :url         "http://xnlogic.com"
  :license     {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version  "2.0.0"
  :dependencies   [[org.clojure/clojure "1.5.1"]
                   [core.async "0.1.0-SNAPSHOT"] ]
  :global-vars {*warn-on-reflection* true}
  :profiles
  {:dev
   {:source-paths      ["src/clojure" "dev"]
    :dependencies      [[expectations "1.3.8" :exclusions [org.clojure/clojure]]
                        [org.clojure/tools.namespace "0.2.3"]
                        [criterium "0.4.1"]
                        [repetition-hunter "0.3.1"]]}
   :integration
   {:source-paths      ["src/clojure"]
    :java-source-paths ["src/java"]
    :test-paths        ["test_integration"]
    :target-path       "target/integration"
    :dependencies      [[expectations "1.3.8" :exclusions [org.clojure/clojure]]
                        [com.tinkerpop.blueprints/blueprints-core "2.3.0"] ]}
   }
  :plugins [[lein-expectations "0.0.7"]
            [lein-autoexpect "0.2.5"]
            [lein-kibit "0.0.7"]
            ])
