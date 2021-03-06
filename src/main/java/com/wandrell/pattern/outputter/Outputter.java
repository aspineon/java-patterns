/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014-2015 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wandrell.pattern.outputter;

import java.io.OutputStream;
import java.io.Writer;

/**
 * Interface for the outputter pattern. This allows sending data through an I/O
 * output operation, without having to worry about the concrete implementation
 * or API being used.
 * <p>
 * For example, if a file has to be saved into a file, an {@code Outputter} can
 * be used. That way the file format can be changed just by swapping the current
 * {@code Outputter} for another implementation.
 * <p>
 * It should be noted that this interface received the output class to be used
 * directly on it's methods, instead of it being set on a constructor or a
 * setter, because instances of {@code Outputter} are expected to close the
 * output object once the operation is finished.
 * 
 * @author Bernardo Martínez Garrido
 * @param <V>
 *            the type to be sent through the output operation
 */
public interface Outputter<V> {

    /**
     * Sends an object through an {@code OutputStream}.
     * <p>
     * The stream is expected to be closed once the operation is finished.
     * 
     * @param value
     *            object to send
     * @param stream
     *            {@code OutputStream} to receive the parsed object
     * @throws Exception
     *             if sending the object causes an error
     */
    public void output(final V value, final OutputStream stream)
            throws Exception;

    /**
     * Sends an object through a {@code Writer}.
     * <p>
     * The reader is expected to be closed once the operation is finished.
     * 
     * @param value
     *            object to send
     * @param writer
     *            {@code Writer} to receive the parsed object
     * @throws Exception
     *             if sending the object causes an error
     */
    public void output(final V value, final Writer writer) throws Exception;

}
