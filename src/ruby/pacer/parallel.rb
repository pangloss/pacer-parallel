require "pacer/parallel/version"
require File.join(Pacer::Parallel::PATH, Pacer::Parallel::JAR_PATH)
require "pacer/clojure"
require 'pacer'
require "pacer/parallel/channel_cap"
require "pacer/parallel/channel_reader"
require "pacer/parallel/channel_fan_in"

module Pacer
  module Routes
    module RouteOperations
      def parallel(opts = {}, &block)
        threads = opts.fetch(:threads, 2)
        if threads > 0
          branched = (0...threads).reduce(channel_cap buffer: opts.fetch(:in_buffer, threads)) do |r, n|
            r.branch do |x|
              b = block.call x.channel_reader(based_on: self)
              b.channel_cap buffer: opts.fetch(:out_buffer, threads)
            end
          end
          branched.merge_exhaustive.gather.channel_fan_in(based_on: block.call(self))
        else
          block.call self
        end
      end
    end
  end
end
