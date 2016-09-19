(ns synth-tree.core
  (:use [synth-tree.oscilators :only [oscilator]]
        [synth-tree.compositors :only [add]]
        [synth-tree.basic])
  (:require [synth-tree.glob :as G])
  (:import [maa.synthTree SynthNode]
           [javax.sound.sampled AudioFormat AudioSystem SourceDataLine]
           [java.nio ByteBuffer]))

; test: proof of concept

(def arr (float-array 44100))

(def af (AudioFormat. G/sample-rate 32 1 true true))

(defn -main []
  (println "start")
  (def s (add
           ;(oscilator :sine 66)
           ;(oscilator :sine 77)
           ;(oscilator :square 440)
           ;(oscilator :harmonic :C4)
           ;(oscilator :sine :C4)
           ;(oscilator :pow :C4 1.3)
           ;(oscilator :square :C3)
           ;(oscilator :root :F3)
           (oscilator :pow :F3 2)
           ))

  (samples-at s 0 44100 arr)

  (println "generated")

  (doto (AudioSystem/getSourceDataLine af)
    (.open af)
    (.start)
    (.write (samples2bytes arr) 0 (* 4 44100))
    (.drain)
    (.stop)
    (.close)
    )
  (println "end"))

'( 1 2 3 (4 [5 6] 7 8 9) 10 11 12)

5

