(ns synth-tree.basic
  (:require [synth-tree.glob :as G])
  (:import [maa.synthTree SynthNode Volume]
           [java.nio ByteBuffer]))

(defn samples-at
  ([^SynthNode s pos len] (.samplesAt s pos len))
  ([^SynthNode s pos len arr] (.samplesAt s pos len arr))
  )
(defn begin [^SynthNode n] (.begin n))
(defn end [^SynthNode n] (.end n))


(defn volume
  "[volume:float source:node]"
  [vol node] (Volume. vol node))


(defn- float2int [f]
  (if (> f Integer/MAX_VALUE)
    Integer/MAX_VALUE
    (if (< f Integer/MIN_VALUE)
      Integer/MIN_VALUE
      (int f))))

(defn- sample2int [s] (float2int (* 0.9 Integer/MAX_VALUE s)))
(defn samples2bytes "[float](::sample-chunk) -> [sInt32 as bytes]"
  [^floats arr]
  (let [bb (ByteBuffer/allocate (* 44100 4))]

    (loop [i 0]
      (when (< i (count arr))
        (.putInt bb (sample2int (aget arr i)))
        (recur (inc i))))

    (.array bb)))
