(ns synth-tree.oscilators
  (:use [synth-tree.basic]
        [synth-tree.compositors :only [add]])
  (:require [synth-tree.glob :as G]
            [synth-tree.named-tones :as nt])
  (:import [maa.synthTree SynthNode OscilatorSine OscilatorSquare OscilatorRoot]))

  ;; freq: in hertz
  ;; result: -1.0 - +1.0
(defn sine [freq] (OscilatorSine. (nt/unname freq) G/sample-rate))
(defn root
  ([freq] (root freq 155))
  ([freq r] (OscilatorRoot. (float r) (nt/unname freq) G/sample-rate))
  )
(defn pow ;unexpectedly nice sound (probably some nice harmonic)
  ([freq] (pow freq 2))
  ([freq r] (root freq (/ 1 r)))
  )
(defn square [freq] (OscilatorSquare. (nt/unname freq) G/sample-rate))




(defn harmonic "hs: map of nth harmonic to it's volume (first being self (1st harmonic))"
  ; osc[ilator] :: freq -> SynthNode
  ([freq] (harmonic freq [1 0.01 0.01 0.01 0.1 0.1 0.09 0.05 0.01 0.001])) ;just some random default...
  ([freq hs] (harmonic freq sine hs))
  ([freq osc hs]
   (let [freq (nt/unname freq)
         hs (if (vector? hs)
              (map-indexed (fn [i v] [(inc i) v]) hs)
              (seq hs))]
     (apply add (map (fn [[h v]] ; nth Harmonic , volume
                       (volume v (osc (* freq h))))
                     hs)))))



;oscilators
(def oss (atom { :sine sine
                 :root root
                 :pow pow
                 :square square
                 :harmonic harmonic
                 }))

(defn oscilator [typ & args] (apply (@oss typ) args))


; idea: harmonic
; like sine, but composes it's harmonics in some way (quieter....)
