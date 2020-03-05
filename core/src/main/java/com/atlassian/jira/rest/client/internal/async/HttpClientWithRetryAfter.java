package com.atlassian.jira.rest.client.internal.async;

import com.atlassian.httpclient.api.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import io.atlassian.util.concurrent.Promises;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * JIRA returns a lot of "HTTP 429" responses with Retry-After headers.
 * The old versions of the client libs used to have an extension point
 * where we could add a retry handler, but apache http 4 appears not to.
 * See e.g. https://github.com/AsyncHttpClient/async-http-client/issues/1404
 * and https://github.com/AsyncHttpClient/async-http-client/issues/1007
 */
public class HttpClientWithRetryAfter implements HttpClient {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HttpClient inner;

    public HttpClientWithRetryAfter(HttpClient inner) {
        this.inner = inner;
    }

    @Override
    public Request.Builder newRequest() {
        return inner.newRequest();
    }

    @Override
    public Request.Builder newRequest(URI uri) {
        return inner.newRequest(uri);
    }

    @Override
    public Request.Builder newRequest(String s) {
        return inner.newRequest(s);
    }

    @Override
    public Request.Builder newRequest(URI uri, String s, String s1) {
        return inner.newRequest(uri, s, s1);
    }

    @Override
    public Request.Builder newRequest(String s, String s1, String s2) {
        return inner.newRequest(s, s1, s2);
    }

    @Override
    public void flushCacheByUriPattern(Pattern pattern) {
        inner.flushCacheByUriPattern(pattern);
    }

    @Override
    public <A> ResponseTransformation.Builder<A> transformation() {
        return inner.transformation();
    }

    @Override
    public ResponsePromise execute(Request request) {
        return ResponsePromises.toResponsePromise(inner.execute(request).flatMap(response -> {
            if (response.getStatusCode() == 429) {
                int waitSeconds = ImmutableList.of("Retry-After", "Retry-after", "retry-after").stream()
                        .map(response::getHeader)
                        .filter(Objects::nonNull)
                        .map(Integer::parseInt)
                        .findFirst()
                        .orElse(5);
                log.info("Saw `HTTP 429 Too Many Requests`, waiting for {} seconds", waitSeconds);
                try {
                    Thread.sleep(1000 * waitSeconds);
                } catch (InterruptedException e) {
                }
                return inner.execute(request);
            } else {
                return Promises.promise(response);
            }
        }));
    }
}
