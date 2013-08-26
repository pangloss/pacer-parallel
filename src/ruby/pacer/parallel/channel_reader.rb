module Pacer
  module Routes
    module RouteOperations
      # The source of this in a pipe emitting individual channels
      def channel_reader(opts = {})
        chain_route(opts.merge transform: Pacer::Transform::ChannelReader)
      end
    end
  end

  module Parallel
    module ChannelReader
      import com.xnlogic.pacer.ChannelReaderPipe

      protected

      def attach_pipe(end_pipe)
        pipe = ChannelReaderPipe.new
        pipe.setStarts end_pipe if end_pipe
        pipe
      end
    end
  end
end
