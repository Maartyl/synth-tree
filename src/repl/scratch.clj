(ns scratch
  (:use [synth-tree.oscilators :only [oscilator]]
        [synth-tree.compositors :only [add]]
        [synth-tree.basic]
        [synth-tree.core])
  (:require [synth-tree.glob :as G])
  (:import [maa.synthTree SynthNode]
           [javax.sound.sampled AudioFormat AudioSystem SourceDataLine]
           [java.nio ByteBuffer]))


(play-tree (add
             ;(oscilator :pow :C4 1.0)
             ;(oscilator :pow :E4 1.0)
             (oscilator :pow :G4 1.0)

             ))



