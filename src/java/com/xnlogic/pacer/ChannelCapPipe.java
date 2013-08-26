package com.xnlogic.pacer;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;

import clojure.lang.RT;
import clojure.lang.Var;

public class ChannelCapPipe<S> extends AbstractPipe<S, Object> implements TransformPipe<S, Object> {
  private static final Var PIPE_TO_CHAN = RT.var("pacer.parallel", "pipe->chan");

  private Object buffer;
  private boolean hasRun = false;

  public ChannelCapPipe(Object buffer) {
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
