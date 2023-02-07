package webserver.handlermapper;

import org.springframework.http.HttpMethod;
import webserver.HttpRequest;

import java.util.Objects;

public class RequestMappingInfo {
    private final String path;
    private final HttpMethod httpMethod;

    public RequestMappingInfo(String path, HttpMethod httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }

    public static RequestMappingInfo of(HttpRequest request) {
        return new RequestMappingInfo(
                request.getRequestPath().orElseThrow(IllegalArgumentException::new),
                request.getHttpMethod()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMappingInfo that = (RequestMappingInfo) o;
        return Objects.equals(path, that.path) && httpMethod == that.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, httpMethod);
    }
}
