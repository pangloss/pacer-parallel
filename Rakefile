require "bundler/gem_tasks"

task :package do
  system 'lein', 'with-profile', 'release', 'uberjar'
end

file Pacer::Parallel::JAR_PATH => FileList['project.clj', 'src/clojure/**/*.clj', 'src/java/**/*.java'] do
  Rake::Task['package'].execute
end

task :jar => Pacer::Parallel::JAR_PATH

task :build => :jar
task :install => :jar

desc "Run the clojure test and integration suites"
task :expectations do
  sh "lein with-profile integration expectations"
end
