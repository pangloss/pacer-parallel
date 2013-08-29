(ns pacer.parallel
  (:require [clojure.core.async :refer [chan >!! close! alts!!]])
  (:import com.tinkerpop.pipes.Pipe))

(defn pipe->chan [^Pipe pipe buffer]
  (let [c (if buffer (chan buffer) (chan))]
    (future
      (try
        (loop [v (.next pipe)]
          (when v (>!! c v))
          (recur (.next pipe)))
        (finally
          (close! c))))
    c))

(defn pipe->path-chan [^Pipe pipe buffer]
  (let [c (if buffer (chan buffer) (chan))]
    (future
      (try
        (loop [v (.next pipe) path (.getCurrentPath pipe)]
          (when v (>!! c path))
          (recur (.next pipe) (.getCurrentPath pipe)))
        (finally
          (close! c))))
    c))

(defn chan-select [chans]
  (let [[v c] (alts!! chans)]
    (if v
      [v chans]
      (if (= 1 (count chans))
        nil
        (recur (remove #{c} chans))))))
