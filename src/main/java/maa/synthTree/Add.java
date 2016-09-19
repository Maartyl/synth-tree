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
 * adds multiple synthNodes (sample wise)
 * @author maartyl
 */
public final class Add implements SynthNode {

  private long start = Long.MAX_VALUE;
  private long end = 0;
  private final SynthNode[] nodes;

  public Add(SynthNode[] nodes) {
    if (nodes == null)
      throw new IllegalArgumentException("Add.nodes cannot be null");
    this.nodes = nodes;
    
    if (nodes.length == 0){ //empty: 
      start = 0;
      end = 0;
      return;
    }

    for (SynthNode node : nodes) {
      final long s = node.begin();
      final long e = node.end();
      if (s < start) start = s;
      if (e > end)   end = e;
    }
  }

  @Override
  public long begin() {
    return start;
  }
  @Override
  public long end() {
    return end;
  }
  
  private void add2(float[] arrL, float[] arrR, int length){
    for (int i = 0; i < length; i++) 
      arrL[i] += arrR[i];
  }
  
  private float[] samplesMulti(long position, int length, float[] arr){
    assert nodes.length >= 2;
    float[] arrAcc = nodes[0].samplesAt(position, length, arr);
    float[] arrCur = nodes[1].samplesAt(position, length);
    add2(arrAcc, arrCur, length);
    
    for (int i = 2; i < nodes.length; i++) {
      SynthNode node = nodes[i];
      if (node.begin() > (position+length) || 
          node.end() < position) //would only add 0s
        continue;
      
      arrCur = node.samplesAt(position, length, arrCur);
      add2(arrAcc, arrCur, length);
    }
    return arrAcc;
  }

  @Override
  public float[] samplesAt_impl(long position, float[] arr, int arrStart, int arrEnd) {
    switch (nodes.length) {
      case 0: //fill with zeroes
        return Zero.SINGLETON.samplesAt(position, arrEnd, arr);
      case 1:
        return nodes[0].samplesAt(position, arrEnd, arr);
      default:
        return samplesMulti(position, arrEnd, arr);
    }
  }

}
