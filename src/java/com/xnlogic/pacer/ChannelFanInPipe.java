package com.xnlogic.pacer;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;

import clojure.lang.RT;
import clojure.lang.Var;

public class ChannelFanInPipe<S> extends AbstractPipe<List<Object>, S> implements TransformPipe<List<Object>, S> {
  private static final Var READ_A_CHAN = RT.var("core.async", "alts!!");
  private static final Var FIRST = RT.var("clojure.core", "first");

  private Object chans;

  protected Object processNextStart() {
    while true {
      if (!this.chans) {
        this.chans = starts.next()
      } else {
        // alts!! returns [value chan]
        Object vec = READ_A_CHAN.invoke(this.chans);
        if (vec == null)
          this.chans = null;
        else
          return FIRST.invoke(vec);
      }
    }
  }

  public void reset() {
    this.chans = null;
  }
}

