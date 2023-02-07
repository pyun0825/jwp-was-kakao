package webserver.request;

import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    @NonNull
    private final HttpMethod httpMethod;
    @NonNull
    private final String url;
    @NonNull
    private final String httpVersion;
    private final Map<String, String> headers;

    public RequestHeader(HttpMethod httpMethod, String url, String httpVersion) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.httpVersion = httpVersion;
        this.headers = new HashMap<>();
    }

    public void put(String key, String value) {
        this.headers.put(key, value);
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public String getUrl() {
        return this.url;
    }

    public Optional<String> getPath() {
        return Optional.ofNullable(this.url.split("\\?")[0]);
    }

    public Optional<String> getQuery() {
        return Optional.ofNullable(this.url.split("\\?")[1]);
    }

    public boolean hasContentLength() {
        return this.headers.containsKey("Content-Length");
    }

    public Integer getContentLength() {
        return Integer.parseInt(this.headers.get("Content-Length"));
    }
}
