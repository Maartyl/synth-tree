
package maa.synthTree;

/**
 * Full definition requires:
 *  - duration
 *  - sampleAt | samplesAt_impl
 *
 * @author maartyl
 */
public interface SynthNode {
    // units: samples
    // infinite length: long.Max (6 million years (at 44100Hz) seems enough to me ^^)
    default long duration(){
        return Long.MAX_VALUE; //infinite (like a simple oscilator or so)
    }
    
    //defines sound value at the position in 'sample-stream'
    // this is more for convenience dor simple nodes which would just independently store samples in the array
    // this should never be actueally called from the tree
    default float sampleAt(long position){
        float[] arr = samplesAt(position, 1);
        return arr[0];
    }
    
    //float loli();
    
    
    // implementation variant that will NEVER be called from outside of the class
    // interfaces just don't allow protected defaults...
    default float[] samplesAt_impl(long position, int length, float[] arr) {
        for (int i = 0; i < length; ++i)
            arr[i] = sampleAt(position +i);
        
        return arr;
    }
    
    //can use argument array, for reuse
    // it should generally be long enough
    // the point of this is just speed: works just like many sampleAt
    // always fills from 0
    default float[] samplesAt(long position, int length, float[] arr){
        if ((arr == null || arr.length < length))
            return samplesAt(position, length); //reuse creation of array in other overloed
        
        return samplesAt_impl(position, length, arr);
    }
    default float[] samplesAt(long position, int length){
        return samplesAt(position, length, new float[length]);
    }
}
