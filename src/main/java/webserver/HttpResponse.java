package webserver;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    @NonNull
    private final ResponseHeader responseHeader;
    @Nullable
    private byte[] body;

    private HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.responseHeader = new ResponseHeader(httpStatus);
        this.body = body;
    }

    public static HttpResponse ok() {
        return new HttpResponse(HttpStatus.OK, null);
    }

    public static HttpResponse ok(byte[] body) {
        return new HttpResponse(HttpStatus.OK, body);
    }

    public static HttpResponse found() {
        return new HttpResponse(HttpStatus.FOUND, null);
    }

    public static HttpResponse found(byte[] body) {
        return new HttpResponse(HttpStatus.FOUND, body);
    }

    public HttpResponse setContentType(String contentType) {
        this.responseHeader.addContentType(contentType);
        return this;
    }

    public HttpResponse setContentLength() {
        this.responseHeader.addContentLength(body == null ? 0 : body.length);
        return this;
    }

    public HttpResponse setLocation(String location) {
        this.responseHeader.addLocation(location);
        return this;
    }

    public HttpResponse body(byte[] body) {
        this.body = body;
        return this;
    }

    public void sendResponse(DataOutputStream dos) throws IOException {
        dos.writeBytes(this.responseHeader.buildToString());
        if (body != null) {
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }
}
