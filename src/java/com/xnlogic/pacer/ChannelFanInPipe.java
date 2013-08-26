package com.xnlogic.pacer;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;

import java.util.List;
import clojure.lang.RT;
import clojure.lang.Var;

public class ChannelFanInPipe<S> extends AbstractPipe<List<Object>, S> implements TransformPipe<List<Object>, S> {
  private static final Var VEC = RT.var("clojure.core", "vec");
  private static final Var NTH = RT.var("clojure.core", "nth");
  private static final Var CHAN_SELECT = RT.var("pacer.parallel", "chan-select");

  private Object chans;

  protected S processNextStart() {
    while (true) {
      if (this.chans == null) {
        Object next = starts.next();
        if (next != null)
          this.chans = VEC.invoke(next);
      } else {
        Object vec = CHAN_SELECT.invoke(this.chans);
        if (vec == null) {
          this.chans = null;
        } else {
          this.chans = NTH.invoke(vec, 1);
          return (S) NTH.invoke(vec, 0);
        }
      }
    }
  }

  public void reset() {
    this.chans = null;
  }
}

