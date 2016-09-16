(ns synth-tree.oscilators
  (:require [synth-tree.glob :as G])
  (:import [maa.synthTree SynthNode]))


(deftype Sin [angf]
  SynthNode
  (sampleAt [_ pos] (Math/sin (* pos angf))))

  ;; freq: in hertz
  ;; result: -1.0 - +1.0
(defn sine [freq] (Sin. (/ (* Math/PI 2.0 freq) G/sample-rate)))


;oscilators
(def oss (atom {:sine sine}))

(defn oscilator [typ & args] (apply (@oss typ) args))
