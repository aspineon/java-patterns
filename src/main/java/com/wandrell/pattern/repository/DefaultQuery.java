/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 the original author or authors.
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
package com.wandrell.pattern.repository;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Map;

/**
 * Default implementation of {@link Query}.
 * <p>
 * This is an immutable class, which will just store the data to be used in a
 * query, not allowing it to be edited.
 * 
 * @author Bernardo Martínez Garrido
 */
public final class DefaultQuery implements Query {

    /**
     * Parameters for the query.
     * <p>
     * These will be set into the query string inside the
     * {@code com.wandrell.pattern.repository.Repository Repository}, adapting
     * it to the API being used.
     */
    private final Map<String, Object> params;
    /**
     * The string for the query.
     */
    private final String              queryStr;

    /**
     * Constructs a {@code DefaultQuery} with the query's data.
     * 
     * @param query
     *            the query string
     * @param parameters
     *            the query's parameters
     */
    public DefaultQuery(final String query, final Map<String, Object> parameters) {
        super();

        checkNotNull(query, "Received a null pointer as query");
        checkNotNull(parameters, "Received a null pointer as parameters");

        queryStr = query;
        params = parameters;
    }

    @Override
    public final Map<String, Object> getParameters() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public final String getQuery() {
        return queryStr;
    }

}