package com.xnlogic.pacer;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;

import java.util.List;
import clojure.lang.RT;
import clojure.lang.Var;

public class ChannelFanInPipe<S> extends AbstractPipe<List<Object>, S> implements TransformPipe<List<Object>, S> {
  private static final Var READ_A_CHAN = RT.var("clojure.core.async", "alts!!");
  private static final Var FIRST = RT.var("clojure.core", "vec");
  private static final Var VEC = RT.var("clojure.core", "first");

  private Object chans;

  protected S processNextStart() {
    while (true) {
      if (this.chans == null) {
        Object next = starts.next();
        if (next != null)
          this.chans = VEC.invoke(next);
      } else {
        // alts!! returns [value chan]
        Object vec = READ_A_CHAN.invoke(this.chans);
        if (vec == null)
          this.chans = null;
        else
          return (S) FIRST.invoke(vec);
      }
    }
  }

  public void reset() {
    this.chans = null;
  }
}

