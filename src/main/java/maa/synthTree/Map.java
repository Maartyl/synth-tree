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
 *
 * @author maartyl
 */
public abstract class Map implements SynthNode {
  
  private SynthNode source;

  public Map(SynthNode source) {
    if (source == null)
      throw new IllegalArgumentException("Map.source cannot be null");
    this.source = source;
  }

  @Override
  public long begin() {
    return source.begin();
  }

  @Override
  public long end() {
    return source.end();
  }
  
  abstract float map(long position, float sample);

  @Override
  public float[] samplesAt_impl(long position, float[] arr, int arrStart, int arrEnd) {
    arr = source.samplesAt(position, arrEnd, arr, arrStart);
    for (int i = arrStart; i < arrEnd; i++) {
      arr[i] = map(position+i, arr[i]);
    }
    return arr;
  }
  
}
