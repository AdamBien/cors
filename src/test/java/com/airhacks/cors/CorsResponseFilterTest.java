package com.airhacks.cors;

/*
 * #%L
 * jaxrs-cors
 * %%
 * Copyright (C) 2014 Adam Bien
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import java.util.ArrayList;
import java.util.List;

import static com.airhacks.cors.CorsResponseFilter.DEFAULT_ALLOWED_HEADERS;
import static com.airhacks.cors.CorsResponseFilter.DEFAULT_EXPOSED_HEADERS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author airhacks.com
 */
public class CorsResponseFilterTest {

    private CorsResponseFilter cut;

    @Before
    public void init() {
        this.cut = new CorsResponseFilter();
    }

    @Test
    public void noAllowedHeaders() {
        List<String> headers = null;
        String expected = DEFAULT_ALLOWED_HEADERS;
        String actual = cut.createHeaderList(headers, DEFAULT_ALLOWED_HEADERS);
        assertThat(actual, is(expected));
    }

    @Test
    public void multipleAllowedHeaders() {
        List<String> headers = new ArrayList<>();
        headers.add("java");
        headers.add("duke");
        String expected = "java,duke," + DEFAULT_ALLOWED_HEADERS;
        String actual = cut.createHeaderList(headers, DEFAULT_ALLOWED_HEADERS);
        assertThat(actual, is(expected));
    }

    @Test
    public void singleAllowedHeader() {
        List<String> headers = new ArrayList<>();
        headers.add("java");
        String expected = "java," + DEFAULT_ALLOWED_HEADERS;
        String actual = cut.createHeaderList(headers, DEFAULT_ALLOWED_HEADERS);
        assertThat(actual, is(expected));
    }

    @Test
    public void noExposedHeaders() {
        List<String> headers = null;
        String expected = DEFAULT_EXPOSED_HEADERS;
        String actual = cut.createHeaderList(headers, DEFAULT_EXPOSED_HEADERS);
        assertThat(actual, is(expected));
    }

    @Test
    public void multipleExposedHeaders() {
        List<String> headers = new ArrayList<>();
        headers.add("java");
        headers.add("duke");
        String expected = "java,duke," + DEFAULT_EXPOSED_HEADERS;
        String actual = cut.createHeaderList(headers, DEFAULT_EXPOSED_HEADERS);
        assertThat(actual, is(expected));
    }

    @Test
    public void singleExposedHeader() {
        List<String> headers = new ArrayList<>();
        headers.add("java");
        String expected = "java," + DEFAULT_EXPOSED_HEADERS;
        String actual = cut.createHeaderList(headers, DEFAULT_EXPOSED_HEADERS);
        assertThat(actual, is(expected));
    }

    @Test
    public void defaultAllowedHeaders() {
        ContainerRequestContext crc = mock(ContainerRequestContext.class);
        when(crc.getHeaders()).thenReturn(new MultivaluedHashMap<String, String>());
        String expected = DEFAULT_ALLOWED_HEADERS;
        String actual = cut.getRequestedAllowedHeaders(crc);
        assertThat(actual, is(expected));
    }

    @Test
    public void defaultExposedHeaders() {
        ContainerRequestContext crc = mock(ContainerRequestContext.class);
        when(crc.getHeaders()).thenReturn(new MultivaluedHashMap<String, String>());
        String expected = DEFAULT_EXPOSED_HEADERS;
        String actual = cut.getRequestedExposedHeaders(crc);
        assertThat(actual, is(expected));
    }

}
