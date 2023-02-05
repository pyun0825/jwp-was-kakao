package webserver;

import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class HttpRequest {
    @NonNull
    private final Headers headers;
    @Nullable
    private final String body;

    public HttpRequest(Headers headers, String body) {
        this.headers = headers;
        this.body = body;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public HttpMethod getHttpMethod() {
        return headers.getHttpMethod();
    }

    public String getRequestUrl() {
        return headers.getUrl();
    }
}
