(ns pacer.parallel
  (:require [clojure.core.async :refer [chan >!! close!]])
  (:import com.tinkerpop.pipes.Pipe))

(defn pipe->chan [^Pipe pipe buffer]
  (let [c (if buffer (chan buffer) (chan))]
    (if (.hasNext pipe)
      (future
        (loop [v (.next pipe)]
          (when v (>!! c v))
          (if (.hasNext pipe)
            (recur (.next pipe))
            (close! c))))
      (close! c))
    c))
