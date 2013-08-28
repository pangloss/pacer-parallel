(defproject
  pacer.parallel "0.2.0-SNAPSHOT"
  :description "Leveraging core.async in Pacer"
  :url         "http://xnlogic.com"
  :license     {:name "MIT"}
  :min-lein-version  "2.0.0"
  :dependencies   [[org.clojure/clojure "1.5.1"]
                   [core.async "0.1.0-SNAPSHOT"]
                   [com.tinkerpop/pipes "2.3.0"]]
  :global-vars {*warn-on-reflection* true}
  :profiles
  {:dev
   {:source-paths      ["src/clojure"]
    :java-source-paths ["src/java"]}
   :release
   {:source-paths      ["src/clojure"]
    :java-source-paths ["src/java"]
    :target-path       "target/release"}
   :integration
   {:source-paths      ["src/clojure"]
    :java-source-paths ["src/java"]
    :test-paths        ["test_integration"]
    :target-path       "target/integration"}
   }
  :plugins [[lein-expectations "0.0.7"]
            [lein-autoexpect "0.2.5"]
            [lein-kibit "0.0.7"]
            ])
