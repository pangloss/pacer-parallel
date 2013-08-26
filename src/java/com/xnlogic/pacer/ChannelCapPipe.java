package com.xnlogic.pacer;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;

import clojure.lang.RT;
import clojure.lang.Var;
import clojure.lang.Symbol;

public class ChannelCapPipe<S> extends AbstractPipe<S, Object> implements TransformPipe<S, Object> {
  private static final Var REQUIRE = RT.var("clojure.core", "require"); 
  private static final Var PIPE_TO_CHAN = RT.var("pacer.parallel", "pipe->chan");
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
      return PIPE_TO_CHAN.invoke(starts, buffer);
    } else {
      throw FastNoSuchElementException.instance();
    }
  }

  public void reset() {
    this.hasRun = false;
  }
}
