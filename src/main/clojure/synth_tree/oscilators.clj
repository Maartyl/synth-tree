(ns synth-tree.oscilators
  (:require [synth-tree.glob :as G])
  (:import [maa.synthTree SynthNode OscilatorSine]))

  ;; freq: in hertz
  ;; result: -1.0 - +1.0
(defn sine [freq] (OscilatorSine. freq G/sample-rate))


;oscilators
(def oss (atom {:sine sine}))

(defn oscilator [typ & args] (apply (@oss typ) args))


; idea: harmonic
; like sine, but composes it's harmonics in some way (quieter....)
