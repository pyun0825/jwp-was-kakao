package webserver.handler;

import utils.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceGetHandler implements Handler{
    @Override
    public void handleRequest(HttpRequest request, DataOutputStream dos) throws IOException, URISyntaxException {
        String[] splitPath = request.getRequestPath().split("\\.");
        String fileType = splitPath[splitPath.length - 1];
        String filePathPrefix = "./static";
        String filePathPostfix = request.getRequestPath().substring(1);
        if(fileType.equals("html") || fileType.equals("ico")) {
            filePathPrefix = "./templates";
            filePathPostfix = request.getRequestPath();
        }
        byte[] body = FileIoUtils.loadFileFromClasspath(filePathPrefix + filePathPostfix);
        HttpResponse httpResponse = HttpResponse.ok(body).setContentType("text/" + fileType).setContentLength();
        httpResponse.sendResponse(dos);
    }
}
