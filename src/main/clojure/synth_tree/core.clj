(ns synth-tree.core
  (:use [synth-tree.oscilators :only [oscilator]]
        [synth-tree.compositors :only [add]])
  (:require [synth-tree.glob :as G])
  (:import [maa.synthTree SynthNode]
           [javax.sound.sampled AudioFormat AudioSystem SourceDataLine]
           [java.nio ByteBuffer]))

; test: proof of concept

(def arr (float-array 44100))

(def af (AudioFormat. G/sample-rate 32 1 true true))

(defn float2int [f]
  (if (> f Integer/MAX_VALUE)
    Integer/MAX_VALUE
    (if (< f Integer/MIN_VALUE)
      Integer/MIN_VALUE
      (int f))))

(defn arrb []
  (let [bb (ByteBuffer/allocate (* 44100 4))]

    (loop [i 0]
      (when (< i (count arr))
        (.putInt bb (float2int (* Integer/MAX_VALUE (aget arr i))))
        (recur (inc i))))

    (.array bb)

    ))


(defn -main []
  (println "start")
  (def s (add [
                (oscilator :sine 66)
                (oscilator :sine 77)
                (oscilator :square 440)
                ]) )

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




