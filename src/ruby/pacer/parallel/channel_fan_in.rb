module Pacer
  module Routes
    module RouteOperations
      # The source of this should be a pipe emitting lists of channels.
      def channel_fan_in(opts = {})
        chain_route(opts.merge transform: Pacer::Parallel::ChannelFanIn)
      end
    end
  end

  module Parallel
    module ChannelFanIn
      import com.xnlogic.pacer.ChannelFanInPipe

      protected

      def attach_pipe(end_pipe)
        pipe = ChannelFanInPipe.new
        pipe.setStarts end_pipe if end_pipe
        pipe
      end
    end
  end
end



