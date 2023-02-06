package webserver;

import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class HttpRequest {
    @NonNull
    private final RequestHeader requestHeader;
    @Nullable
    private final String body;

    public HttpRequest(RequestHeader requestHeader, String body) {
        this.requestHeader = requestHeader;
        this.body = body;
    }

    public RequestHeader getHeaders() {
        return requestHeader;
    }

    public String getBody() {
        return body;
    }

    public HttpMethod getHttpMethod() {
        return requestHeader.getHttpMethod();
    }

    public String getRequestUrl() {
        return requestHeader.getUrl();
    }
}
