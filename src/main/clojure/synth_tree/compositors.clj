(ns synth-tree.compositors
  (:require [synth-tree.glob :as G])
  (:import [maa.synthTree Add SynthNode]))


(defn add [& nodes] (Add. (into-array SynthNode nodes)))



; tones:
; :A0 = oscilator of length '1 note'
; [:C4 :E4] = chord
; (...) = another synth node, limited to this time
