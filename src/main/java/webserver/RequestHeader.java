package webserver;

import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private final Map<String, String> headers;

    public RequestHeader() {
        this.headers = new HashMap<>();
    }

    public void put(String key, String value) {
        this.headers.put(key, value);
    }

    public HttpMethod getHttpMethod() {
        return HttpMethod.resolve(this.headers.get("Method"));
    }

    public String getUrl() {
        return this.headers.get("URL");
    }

    public boolean hasContentLength() {
        return this.headers.containsKey("Content-Length");
    }

    public Integer getContentLength() {
        return Integer.parseInt(this.headers.get("Content-Length"));
    }
}
