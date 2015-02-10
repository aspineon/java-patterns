/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014 the original author or authors.
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
package com.wandrell.testing.pattern.test.integration.parser.xml.read.exception;

import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.testng.annotations.Test;

import com.wandrell.pattern.ResourceUtils;
import com.wandrell.pattern.parser.InputParser;
import com.wandrell.pattern.parser.xml.XMLValidationType;
import com.wandrell.pattern.parser.xml.input.JDOMDocumentDecoder;
import com.wandrell.pattern.parser.xml.input.SAXInputParser;
import com.wandrell.testing.pattern.framework.conf.FileConf;
import com.wandrell.testing.pattern.framework.conf.XMLConf;

/**
 * Integration tests for {@link SAXInputParser}, checking that exceptions are
 * thrown when validation files are invalid.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>An {@code Exception} is thrown when reading a XML file using an empty
 * validation file, when using DTD or XSD validation.</li>
 * <li>An {@code Exception} is thrown when reading a XML file using an
 * incorrectly formatted validation file, when using DTD or XSD validation.</li>
 * </ol>
 * 
 * @author Bernardo Martínez Garrido
 * @version 0.1.0
 * @see SAXInputParser
 */
public final class ITExceptionBadValidationFileSAXInputParser {

    /**
     * Default constructor.
     */
    public ITExceptionBadValidationFileSAXInputParser() {
        super();
    }

    /**
     * Tests that an {@code Exception} is thrown when reading a XML file using
     * an empty validation file, when using DTD validation.
     * 
     * @throws Exception
     *             always, as part of the test
     */
    @Test(expectedExceptions = Exception.class)
    public final void testRead_DTD_Empty() throws Exception {
        InputParser<Integer> parser = null;    // Tested parser

        parser = new SAXInputParser<Integer>(XMLValidationType.DTD,
                IOUtils.toInputStream(""), getJDOMDocumentProcessorInteger());

        parser.read(ResourceUtils.getClassPathInputStream(XMLConf.INTEGER_READ));
    }

    /**
     * Tests that an {@code Exception} is thrown when reading a XML file using
     * an incorrectly formatted validation file, when using DTD validation.
     * 
     * @throws Exception
     *             always, as part of the test
     */
    @Test(expectedExceptions = Exception.class)
    public final void testRead_DTD_Invalid() throws Exception {
        InputParser<Integer> parser = null;    // Tested parser

        parser = new SAXInputParser<Integer>(XMLValidationType.DTD,
                ResourceUtils.getClassPathInputStream(FileConf.PROPERTIES),
                getJDOMDocumentProcessorInteger());

        parser.read(ResourceUtils.getClassPathInputStream(XMLConf.INTEGER_READ));
    }

    /**
     * Tests that an {@code Exception} is thrown when reading a XML file using
     * an empty validation file, when using XSD validation.
     * 
     * @throws Exception
     *             always, as part of the test
     */
    @Test(expectedExceptions = Exception.class)
    public final void testRead_XSD_Empty() throws Exception {
        InputParser<Integer> parser = null;    // Tested parser

        parser = new SAXInputParser<Integer>(XMLValidationType.XSD,
                IOUtils.toInputStream(""), getJDOMDocumentProcessorInteger());

        parser.read(ResourceUtils.getClassPathInputStream(XMLConf.INTEGER_READ));
    }

    /**
     * Tests that an {@code Exception} is thrown when reading a XML file using
     * an incorrectly formatted validation file, when using XSD validation.
     * 
     * @throws Exception
     *             always, as part of the test
     */
    @Test(expectedExceptions = Exception.class)
    public final void testRead_XSD_Invalid() throws Exception {
        InputParser<Integer> parser = null;    // Tested parser

        parser = new SAXInputParser<Integer>(XMLValidationType.XSD,
                ResourceUtils.getClassPathInputStream(FileConf.PROPERTIES),
                getJDOMDocumentProcessorInteger());

        parser.read(ResourceUtils.getClassPathInputStream(XMLConf.INTEGER_READ));
    }

    /**
     * Returns a placeholder {@code JDOMDocumentInputProcessor}.
     * 
     * @return a placeholder {@code JDOMDocumentInputProcessor}
     */
    private final JDOMDocumentDecoder<Integer>
            getJDOMDocumentProcessorInteger() {
        return new JDOMDocumentDecoder<Integer>() {

            @Override
            public final Integer decode(final Document doc) {
                final Integer value;

                value = Integer.parseInt(doc.getRootElement().getChildText(
                        XMLConf.NODE_VALUE));

                return value;
            }

        };
    }

}