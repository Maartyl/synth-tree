/*
 * The MIT License
 *
 * Copyright 2016 maartyl.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package maa.synthTree;

/**
 * Full definition requires:
 *  ~ correct start,end
 *  - samplesAt_impl
 *
 * @author maartyl
 */
public interface SynthNode {
    // units: samples
    // infinite length: long.Max (6 million years (at 44100Hz) seems enough to me ^^)
    // assumes that parent node uses this node from pos=0; if not: 
    //   a shiftNode must be put between, which shifts bothduration and position in stream
    //@final
    default long duration(){
        return end() - begin();
    }
    //start (like C++) - of what can generate in sample stream
    //only informational
    default long begin(){
        return 0; // 0 =~= start
    }
    //AFTER end (like C++) - of what can generate in sample stream
    //only informational
    default long end(){
        return Long.MAX_VALUE; //infinite (like a simple oscilator or so)
    }

    // implementation variant that will NEVER be called from outside of the class
    // interfaces just don't allow protected defaults...
    // arr should ALWAYS be long enough
    // end is AFTER end (like C++)
    float[] samplesAt_impl(long position, float[] arr, int arrStart, int arrEnd);
    
    // FOR position >= end:
    // - must return 0 (if nothing meaningful)
    // FOR position < start:
    // - must return 0 (if nothing meaningful)
    // (dur:5): p(-1)=0, p(0) = 5, p(1) = -5 ,... p(5) = 0
    // start and end are just useful for shortening computation: this still must work correctly
    
    //defines @length of sound values from the @position in 'sample-stream'
    //can use argument array, for reuse: contents are undefined
    // it should generally be long enough
    // always fills arr from 0
    //TODO: concepts for stereo sound (arr=[lrlrlrlr])
    //@final
    default float[] samplesAt(long position, float[] arr, final int arrStart, final int arrEnd){
        if ((arr == null || arr.length < arrEnd))
            return samplesAt(position, arrEnd - arrStart, arrStart); //reuse creation of array in other overloed
        
        return samplesAt_impl(position, arr, arrStart, arrEnd);
    }
    default float[] samplesAt(long position, int length, float[] arr, final int arrStart){
        if ((arr == null || arr.length < (length+arrStart)))
            return samplesAt(position, length, arrStart); //reuse creation of array in other overloed
        
        return samplesAt_impl(position, arr, arrStart, arrStart+length);
    }
    default float[] samplesAt(long position, int length, float[] arr){
        return samplesAt(position, length, arr, 0);
    }
    //fills new array from arrStart
    default float[] samplesAt(long position, int length, final int arrStart){
        return samplesAt(position, length, new float[length+arrStart], arrStart);
    }
    //fills new array from 0
    default float[] samplesAt(long position, int length){
        return samplesAt(position, length, 0);
    }
    
    //samples are not necessarily only samples: they can be piped into other DYNAMIC nodes to mean:
    // - volume, frequency, .....
}
