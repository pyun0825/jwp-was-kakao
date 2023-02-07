package webserver.request;

import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

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

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

    public HttpMethod getHttpMethod() {
        return requestHeader.getHttpMethod();
    }

    public String getRequestUrl() {
        return requestHeader.getUrl();
    }

    public Optional<String> getRequestPath() {
        return requestHeader.getPath();
    }

    public Optional<String> getRequestQuery() {
        return requestHeader.getQuery();
    }
}
