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
package com.wandrell.testing.pattern.test.integration.parser.xml;

import java.io.Reader;

import org.jdom2.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.pattern.parser.Parser;
import com.wandrell.pattern.parser.xml.NotValidatedXMLFileParser;
import com.wandrell.testing.pattern.framework.conf.XMLConf;
import com.wandrell.testing.pattern.framework.util.ResourceUtils;

/**
 * Integration tests for {@link NotValidatedXMLFileParser}.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>Parsing a XML file returns the expected value.</li>
 * </ol>
 * 
 * @author Bernardo Martínez Garrido
 * @see NotValidatedXMLFileParser
 */
public final class ITNotValidatedXMLFileParser {

    /**
     * Default constructor.
     */
    public ITNotValidatedXMLFileParser() {
        super();
    }

    /**
     * Tests that parsing a XML file returns the expected value.
     * 
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testParse() throws Exception {
        final Parser<Reader, Document> parserA;  // Parser tested
        final Parser<Document, Integer> parserB; // Parser for the result
        final Reader reader; // Reader to the test file
        final Integer value; // Tested result from the parsed file

        parserA = new NotValidatedXMLFileParser();

        parserB = new Parser<Document, Integer>() {

            @Override
            public final Integer parse(final Document doc) {
                final Integer value;

                value = Integer.parseInt(doc.getRootElement().getChildText(
                        XMLConf.NODE_VALUE));

                return value;
            }

        };

        reader = ResourceUtils.getClassPathReader(XMLConf.INTEGER_READ);
        value = parserB.parse(parserA.parse(reader));

        Assert.assertEquals(value, (Integer) 1);
    }

}
