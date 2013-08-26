module Pacer
  module Routes
    module RouteOperations
      # This creates a channel, emits it and spawns a thread
      # that will push all data in the source into the channel.
      def channel_cap(opts = {})
        chain_route(opts.merge transform: Pacer::Transform::ChannelCap, element_type: :channel)
      end
    end
  end

  module Parallel
    module ChannelCap
      import com.xnlogic.pacer.ChannelCapPipe

      attr_accessor :buffer

      protected

      def attach_pipe(end_pipe)
        pipe = ChannelCapPipe.new(buffer)
        pipe.setStarts end_pipe if end_pipe
        pipe
      end
    end
  end
end


