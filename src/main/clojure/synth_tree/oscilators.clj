(ns synth-tree.oscilators
  (:require [synth-tree.glob :as G]
            [synth-tree.named-tones :as nt])
  (:import [maa.synthTree SynthNode OscilatorSine OscilatorSquare OscilatorRoot]))

  ;; freq: in hertz
  ;; result: -1.0 - +1.0
(defn sine [freq] (OscilatorSine. freq G/sample-rate))
(defn root
  ([freq] (root freq 155))
  ([freq r] (OscilatorRoot. (float r) freq G/sample-rate))
  )
(defn square [freq] (OscilatorSquare. freq G/sample-rate))


;oscilators
(def oss (atom {
                 :sine sine
                 :root root
                 :square square
                 }))

(defn oscilator [typ & args] (apply (@oss typ) args))


; idea: harmonic
; like sine, but composes it's harmonics in some way (quieter....)
