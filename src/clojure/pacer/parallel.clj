(ns pacer.parallel
  (:require [core.async :refer [chan >!!]])
  (:import com.tinkerpop.pipes.Pipe))

(defn pipe->chan [^Pipe pipe buffer]
  (let [c (if buffer (chan buffer) (chan))]
    (when (.hasNext pipe)
      (future
        (loop [v (.next pipe)]
          (when v (>!! c v))
          (when (.hasNext pipe) (recur (.next pipe))))))
    c))
