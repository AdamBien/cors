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
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * See http://www.w3.org/TR/cors/
 *
 * @author airhacks.com
 */
@Provider
public class CorsResponseFilter implements ContainerResponseFilter {

    public static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS, HEAD";
    public final static int MAX_AGE = 42 * 60 * 60;
    public final static String DEFAULT_ALLOWED_HEADERS = "origin,accept,content-type";
    public final static String DEFAULT_EXPOSED_HEADERS = "location,info";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        final MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", getRequestedAllowedHeaders(requestContext));
        headers.add("Access-Control-Expose-Headers", getRequestedExposedHeaders(requestContext));
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
        headers.add("Access-Control-Max-Age", MAX_AGE);
        headers.add("x-responded-by", "cors-response-filter");
    }

    String getRequestedAllowedHeaders(ContainerRequestContext responseContext) {
        List<String> headers = responseContext.getHeaders().get("Access-Control-Allow-Headers");
        return createHeaderList(headers, DEFAULT_ALLOWED_HEADERS);
    }

    String getRequestedExposedHeaders(ContainerRequestContext responseContext) {
        List<String> headers = responseContext.getHeaders().get("Access-Control-Expose-Headers");
        return createHeaderList(headers, DEFAULT_EXPOSED_HEADERS);
    }

    String createHeaderList(List<String> headers, String defaultHeaders) {
        if (headers == null || headers.isEmpty()) {
            return defaultHeaders;
        }
        StringBuilder retVal = new StringBuilder();
        for (int i = 0; i < headers.size(); i++) {
            String header = (String) headers.get(i);
            retVal.append(header);
            retVal.append(',');
        }
        retVal.append(defaultHeaders);
        return retVal.toString();
    }

}
