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
import java.io.IOException;
import static java.lang.String.valueOf;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author airhacks.com
 */
@WebFilter(filterName = "CORSFilter", urlPatterns = {"/*"})
public class CORSFilter implements Filter {

    public static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS, HEAD";
    public final static int MAX_AGE = 42 * 60 * 60;
    public final static String DEFAULT_ALLOWED_HEADERS = "origin,accept,content-type";

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", getRequestedHeaders(httpRequest));
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
        httpResponse.setHeader("Access-Control-Max-Age", valueOf(MAX_AGE));
        httpResponse.setHeader("x-responded-by", "cors-response-filter");

        chain.doFilter(request, response);
    }

    String getRequestedHeaders(HttpServletRequest responseContext) {
        Enumeration<String> headers = responseContext.getHeaders("Access-Control-Request-Headers");
        return createHeaderList(headers);
    }

    String createHeaderList(Enumeration<String> headers) {
        if (headers == null || !headers.hasMoreElements()) {
            return DEFAULT_ALLOWED_HEADERS;
        }
        StringBuilder retVal = new StringBuilder();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            retVal.append(header);
            if (headers.hasMoreElements()) {
                retVal.append(',');
            }

        }
        return retVal.toString();
    }

    @Override
    public void destroy() {

    }

}
