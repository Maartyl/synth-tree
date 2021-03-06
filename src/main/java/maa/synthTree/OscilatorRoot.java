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
 * ~square wave
 * this is probably better (???) but certainly slower implementation of a square wave
 * with root value like ~155
 * 
 * root/exponent should always be ODD
 * @author maartyl
 */
public final class OscilatorRoot extends OscilatorAngular {
  private final float exp;

  //default root: 155 (for root 0)
  public OscilatorRoot(float root, float freq, float sampleRate) {
    super(freq, sampleRate);
    if (root == 0) //use default
      root = 155;
    exp = 1/root;    
  }

  @Override
  public float sampleAt(long position) {
    return (float) Math.pow(
            Math.sin(angf * position),
            (exp)); //root makes it more square
  }

}
