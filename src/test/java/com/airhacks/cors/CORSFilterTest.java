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
import java.util.Enumeration;
import java.util.Vector;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class CORSFilterTest {

    CORSFilter cut;

    @Before
    public void init() {
        this.cut = new CORSFilter();
    }

    @Test
    public void noHeaders() {
        Enumeration<String> headers = null;
        String expected = CORSFilter.DEFAULT_ALLOWED_HEADERS;
        String actual = cut.createHeaderList(headers);
        assertThat(actual, is(expected));
    }

    @Test
    public void multipleHeaders() {
        Vector<String> headers = new Vector<>();
        headers.add("java");
        headers.add("duke");
        String expected = "java,duke";
        String actual = cut.createHeaderList(headers.elements());
        assertThat(actual, is(expected));
    }

    @Test
    public void singleHeader() {
        Vector<String> headers = new Vector<>();
        headers.add("java");
        String expected = "java";
        String actual = cut.createHeaderList(headers.elements());
        assertThat(actual, is(expected));
    }

}
