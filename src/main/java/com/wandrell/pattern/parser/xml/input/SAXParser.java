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
package com.wandrell.pattern.parser.xml.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.jdom2.input.sax.XMLReaders;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import com.wandrell.pattern.parser.xml.XMLValidationType;

/**
 * Implementation of {link InputParser} parsing from an XML file using the SAX
 * API, with the help of the JDOM library.
 * <p>
 * For this a {@link org.jdom2.Document Document} is built from the input, which
 * is then sent to a {@link JDOMDocumentDecoder}, which will create the returned
 * object from it.
 * <p>
 * Validation can be applied to the parsed file, in the form of XSD or DTD
 * files.
 * 
 * @author Bernardo Martínez Garrido
 * @version 0.1.0
 * @param <V>
 *            the type to be parsed from the input
 */
public final class SAXParser implements XMLValidatedParser {

    /**
     * The text format accepted for the validation files.
     */
    private static final String ENCODING = "UTF-8";
    /**
     * Builder to transform the input into a {@code Document}.
     * <p>
     * It is lazily instantiated.
     */
    private SAXBuilder          builder;
    /**
     * Stream to the validation file.
     */
    private InputStream         streamValidation;
    /**
     * The type of validation being used.
     */
    private XMLValidationType   validationType;

    /**
     * Constructs a parser with the specified processor and no validation.
     */
    public SAXParser() {
        super();

        validationType = XMLValidationType.NONE;
    }

    /**
     * Constructs a parser with the specified processor and validation.
     * 
     * @param validation
     *            the validation type to use
     * @param validationStream
     *            stream for the validation file
     */
    public SAXParser(final XMLValidationType validation,
            final InputStream validationStream) {
        super();

        checkNotNull(validation, "Received a null pointer as validation type");
        checkNotNull(validationStream,
                "Received a null pointer as validation file stream");

        validationType = validation;

        streamValidation = validationStream;
    }

    @Override
    public final XMLValidationType getValidationType() {
        return validationType;
    }

    @Override
    public final Document parse(final Reader input) throws JDOMException,
            IOException {
        return getBuilder().build(input);
    }

    @Override
    public final void setValidation(final XMLValidationType type,
            final InputStream validationStream) {
        checkNotNull(type, "Received a null pointer as validation type");
        checkNotNull(validationStream,
                "Received a null pointer as validation file stream");

        validationType = type;

        streamValidation = validationStream;
    }

    /**
     * Returns the {@code SAXBuilder} to be used when creating a
     * {@code Document} from the parsed {@code InputStream} or {@code Reader}.
     * <p>
     * It will be created the first time it is required.
     * 
     * @return the {@code SAXBuilder} used
     */
    private final SAXBuilder getBuilder() {
        if (builder == null) {
            builder = new SAXBuilder();

            switch (getValidationType()) {
                case XSD:
                    builder.setXMLReaderFactory(getXSDValidationFactory());
                    break;
                case DTD:
                    builder.setXMLReaderFactory(XMLReaders.DTDVALIDATING);
                    builder.setEntityResolver(getEntityResolver());
                    break;
                default:
            }
        }

        return builder;
    }

    /**
     * Returns the code for the charset used on the validation files.
     * 
     * @return the text format used on the files
     */
    private final String getEncoding() {
        return ENCODING;
    }

    /**
     * Returns the {@code EntityResolver} for the DTD validation process.
     * 
     * @return the {@code EntityResolver} for the DTD validation process
     */
    private final EntityResolver getEntityResolver() {
        return new EntityResolver() {

            private String dtd;

            @Override
            public final InputSource resolveEntity(final String publicId,
                    final String systemId) throws IOException {
                final StringBuilder readDTD;
                final InputSource source;
                BufferedReader reader;
                String line;

                if (dtd == null) {
                    reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(
                                getValidationInputStream(), getEncoding()));

                        readDTD = new StringBuilder();
                        line = reader.readLine();
                        while (line != null) {
                            readDTD.append(line);
                            line = reader.readLine();
                        }
                    } finally {
                        if (reader != null) {
                            reader.close();
                        }
                    }

                    dtd = readDTD.toString();
                }

                source = new InputSource(IOUtils.toInputStream(dtd,
                        getEncoding()));
                source.setPublicId(publicId);
                source.setSystemId(systemId);

                return source;
            }
        };
    }

    /**
     * Returns the stream for the validation file.
     * <p>
     * It should be noted that this is a single use stream. So the validation
     * data should be parsed once only.
     * 
     * @return the stream for the validation file
     */
    private final InputStream getValidationInputStream() {
        return streamValidation;
    }

    /**
     * Returns the factory for the XSD validation process.
     * 
     * @return the XSD validation factory
     */
    private final XMLReaderJDOMFactory getXSDValidationFactory() {
        final XMLReaderJDOMFactory factoryValidation;
        final Source[] sources;

        try {
            sources = new Source[1];
            sources[0] = new StreamSource(new BufferedReader(
                    new InputStreamReader(getValidationInputStream(),
                            getEncoding())));

            factoryValidation = new XMLReaderXSDFactory(sources);
        } catch (final JDOMException e) {
            throw new RuntimeException(e);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return factoryValidation;
    }

}