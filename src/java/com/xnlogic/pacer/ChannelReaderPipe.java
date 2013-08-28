package com.xnlogic.pacer;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;

import clojure.lang.RT;
import clojure.lang.Var;
import java.util.List;

public class ChannelReaderPipe<S> extends AbstractPipe<Object, S> implements TransformPipe<Object, S> {
  private static final Var READ_CHAN = RT.var("clojure.core.async", "<!!");

  private Object chan;
  private List currentPath;

  protected S processNextStart() {
    while (true) {
      if (this.chan == null) {
        this.chan = starts.next();
      } else {
        Object value;
        if (this.pathEnabled) {
          this.currentPath = (List) READ_CHAN.invoke(this.chan);
          if (this.currentPath != null) {
            value = this.currentPath.get(this.currentPath.size() - 1);
          } else {
            value = null;
          }
        } else {
          value = READ_CHAN.invoke(this.chan);
        }
        if (value == null)
          this.chan = null;
        else
          return (S) value;
      }
    }
  }

  public List getCurrentPath() {
    if (this.pathEnabled) {
      return this.currentPath;
    } else {
      throw new RuntimeException(Pipe.NO_PATH_MESSAGE);
    }
  }

  protected List getPathToHere() {
    return this.currentPath;
  }

  public void reset() {
    this.currentPath = null;
    this.chan = null;
  }
}
