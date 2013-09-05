module Pacer
  module Parallel
    # Be sure to update project.clj
    VERSION = "0.2.1"
    JAR_VERSION = "#{VERSION}-SNAPSHOT"
    JAR = "pacer.parallel-#{ JAR_VERSION }-standalone.jar"
    JAR_PATH = "target/release/#{ JAR }"
    PATH = File.expand_path(File.join(File.dirname(__FILE__), '../../../..'))
  end
end
