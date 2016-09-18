(defproject synth-tree "0.1.0-SNAPSHOT"
  :main "synth-tree.core"
  :source-paths ["src" "src/main/clojure"]
  :java-source-paths ["src/main/java" "test/main/java"]
  :description "simple idea of a tree structured synthesizer"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :global-vars {*warn-on-reflection* true}
  :dependencies [[org.clojure/clojure "1.8.0"]])
