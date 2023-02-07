package webserver.handler;

import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Handler {
    void handleRequest(HttpRequest request, DataOutputStream dos) throws IOException, URISyntaxException;
}
