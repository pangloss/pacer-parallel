# coding: utf-8
lib = File.expand_path('../src/ruby', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'pacer/parallel/version'

Gem::Specification.new do |spec|
  spec.name          = "pacer-parallel"
  spec.version       = Pacer::Parallel::VERSION
  spec.platform    = 'java'
  spec.authors       = ["Darrick Wiebe"]
  spec.email         = ["dw@xnlogic.com"]
  spec.description   = %q{Simple parallel routes in Pacer}
  spec.summary       = %q{With the magic of Clojure's core.async}
  spec.homepage      = "http://xnlogic.com"
  spec.license       = "MIT"

  spec.files         = `git ls-files`.split($/) + [Pacer::Parallel::JAR_PATH]
  spec.executables   = spec.files.grep(%r{^bin/}) { |f| File.basename(f) }
  spec.test_files    = spec.files.grep(%r{^(test|spec|features)/})
  spec.require_paths = ["src/ruby"]

  spec.add_dependency "pacer", "~> 1.3.3"

  spec.add_development_dependency "bundler", "~> 1.3"
  spec.add_development_dependency "rake"
end
