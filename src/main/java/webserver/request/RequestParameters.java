package webserver.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestParameters {
    Map<String, String> params;

    public RequestParameters() {
        this.params = new HashMap<>();
    }

    public void put(String key, String value) {
        this.params.put(key, value);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(this.params.get(key));
    }
}
