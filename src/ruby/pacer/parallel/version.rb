module Pacer
  module Parallel
    VERSION = "0.1.0"
    JAR = "mcfly-#{ Pacer::Parallel::VERSION }-standalone.jar"
    JAR_PATH = "target/release/#{ JAR }"
    PATH = File.expand_path(File.join(File.dirname(__FILE__), '../../..'))
  end
end
