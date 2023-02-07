package webserver.handlermapper;

import org.springframework.http.HttpMethod;
import webserver.HttpRequest;
import webserver.handler.Handler;
import webserver.handler.ResourceGetHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHandlerMapping {
    private final Map<RequestMappingInfo, Handler> mappings;
    private final ResourceGetHandler resourceGetHandler;

    public RequestHandlerMapping() {
        this.mappings = new HashMap<>();
        this.resourceGetHandler = new ResourceGetHandler();
    }

    public RequestHandlerMapping registerHandler(RequestMappingInfo requestMappingInfo, Handler handler) {
        mappings.put(requestMappingInfo, handler);
        return this;
    }

    public RequestHandlerMapping registerHandler(String path, HttpMethod httpMethod, Handler handler) {
        return registerHandler(new RequestMappingInfo(path, httpMethod), handler);
    }

    public Optional<Handler> getHandlerForRequest(HttpRequest httpRequest) {
        if (httpRequest.getRequestPath()
                .orElseThrow(IllegalArgumentException::new)
                .matches(".*\\.[a-z]+$")) {
            return Optional.of(resourceGetHandler);
        }
        return Optional.ofNullable(mappings.get(RequestMappingInfo.of(httpRequest)));
    }
}
