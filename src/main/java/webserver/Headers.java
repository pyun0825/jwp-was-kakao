package webserver;

import java.util.HashMap;
import java.util.Map;

public class Headers {
    Map<String, String> headers;

    public Headers() {
        this.headers = new HashMap<>();
    }

    public void put(String key, String value) {
        this.headers.put(key, value);
    }

    public String getHttpMethod() {
        return this.headers.get("Method");
    }

    public String getUrl() {
        return this.headers.get("URL");
    }

    public Integer getContentLength() {
        return Integer.parseInt(this.headers.get("Content-Length"));
    }
}
