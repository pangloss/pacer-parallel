package com.xnlogic.pacer;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;

import clojure.lang.RT;
import clojure.lang.Var;

public class ChannelReaderPipe<S> extends AbstractPipe<S, Object> implements TransformPipe<S, Object> {
  private static final Var READ_CHAN = RT.var("clojure.core.async", "<!!");

  private Object chan;

  protected Object processNextStart() {
    while (true) {
      if (this.chan == null) {
        this.chan = starts.next();
      } else {
        Object value = READ_CHAN.invoke(this.chan);
        if (value == null)
          this.chan = null;
        else
          return value;
      }
    }
  }

  public void reset() {
    this.chan = null;
  }
}
