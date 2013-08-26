(ns pacer.parallel
  (:require [clojure.core.async :refer [chan >!! close! alts!!]])
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

(defn chan-select [chans]
  (let [[v c] (alts!! chans)]
    (if v
      [v chans]
      (if (= 1 (count chans))
        nil
        (recur (remove #{c} chans))))))
