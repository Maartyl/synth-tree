(ns synth-tree.core
  (:use [synth-tree.oscilators :only [oscilator]])
  (:require [synth-tree.glob :as G])
  (:import [maa.synthTree SynthNode]
           [javax.sound.sampled AudioFormat AudioSystem SourceDataLine]
           [java.nio ByteBuffer]))


(def arr (float-array 44100))

(def af (AudioFormat. G/sample-rate 32 1 true true))

(defn arrb []
  (let [bb (ByteBuffer/allocate (* 44100 4))]

    (loop [i 0]
      (when (< i (count arr))
        (.putInt bb (int (* Integer/MAX_VALUE (aget arr i))))
        (recur (inc i))))

    (.array bb)

    ))


(defn -main []
  (println "start")
  (def s (oscilator :sine 666))

  (.samplesAt s 0 44100 arr)
  (println "generated")

  (doto (AudioSystem/getSourceDataLine af)
    (.open af)
    (.start)
    (.write (arrb) 0 (* 4 44100))
    (.drain)
    (.stop)
    (.close)
    )
  (println "end"))




