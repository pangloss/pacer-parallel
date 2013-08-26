require "pacer/parallel/version"
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
        branched = (0..threads).reduce(channel_cap buffer: opts.fetch(:in_buffer, threads)) do |r, n|
          r.branch do |x|
            b = block.call x.channel_reader
            b.channel_cap
          end
        end
        branched.merge_exhaustive.gather.channel_fan_in(buffer: opts.fetch(:out_buffer, threads),
                                                        based_on: block.call(self))
      end
    end
  end
end

Pacer::Clojure.require 'clojure.core'
Pacer::Clojure.require 'pacer.parallel'
