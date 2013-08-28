package com.xnlogic.pacer;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;

import clojure.lang.RT;
import clojure.lang.Var;
import clojure.lang.Symbol;
import java.util.List;
import java.util.ArrayList;

public class ChannelCapPipe<S> extends AbstractPipe<S, Object> implements TransformPipe<S, Object> {
  private static final Var REQUIRE = RT.var("clojure.core", "require"); 
  private static final Var PIPE_TO_CHAN = RT.var("pacer.parallel", "pipe->chan");
  private static final Var PIPE_TO_PATH_CHAN = RT.var("pacer.parallel", "pipe->path-chan");
  private static boolean environmentReady = false;

  public static void setupEnvironment() {
    if (!ChannelCapPipe.environmentReady) {
      REQUIRE.invoke(Symbol.intern(null, "clojure.core"));
      REQUIRE.invoke(Symbol.intern(null, "pacer.parallel"));
      REQUIRE.invoke(Symbol.intern(null, "clojure.core.async"));
      ChannelCapPipe.environmentReady = true;
    }
  }

  private Object buffer;
  private boolean hasRun = false;

  public ChannelCapPipe(Object buffer) {
    ChannelCapPipe.setupEnvironment();
    this.buffer = buffer;
  }

  protected Object processNextStart() {
    if (!this.hasRun) {
      this.hasRun = true;
      if (this.pathEnabled) {
        return PIPE_TO_PATH_CHAN.invoke(starts, buffer);
      } else {
        return PIPE_TO_CHAN.invoke(starts, buffer);
      }
    } else {
      throw FastNoSuchElementException.instance();
    }
  }

  // If path is enabled, the path will be pushed into the channel.
  // Having a path available here as well causes the merge pipe to
  // produce a messed up path.
  public List getCurrentPath() {
    return new ArrayList();
  }

  public void reset() {
    this.hasRun = false;
  }
}
