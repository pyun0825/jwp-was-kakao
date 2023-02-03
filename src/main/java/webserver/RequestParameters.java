package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestParameters {
    Map<String, String> params;

    public RequestParameters() {
        this.params = new HashMap<>();
    }

    public void put(String key, String value) {
        this.params.put(key, value);
    }

    public String get(String key) {
        return this.params.get(key);
    }
}
