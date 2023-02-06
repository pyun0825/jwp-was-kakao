package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import utils.HeaderParser;
import utils.IOUtils;
import utils.RequestParameterParser;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             InputStreamReader isr = new InputStreamReader(in);
             BufferedReader br = new BufferedReader(isr);
             OutputStream out = connection.getOutputStream();
             DataOutputStream dos = new DataOutputStream(out))
        {
            HttpRequest httpRequest = getHttpRequestFromInput(br);
            if (HttpMethod.GET.equals(httpRequest.getHttpMethod())) {
                handleGet(httpRequest, dos);
            }
            if (HttpMethod.POST.equals(httpRequest.getHttpMethod())) {
                handlePost(httpRequest, dos);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    private HttpRequest getHttpRequestFromInput(BufferedReader br) throws IOException {
        RequestHeader requestHeader = HeaderParser.parse(br);
        String body = null;
        if (requestHeader.hasContentLength()) {
            body = IOUtils.readData(br, requestHeader.getContentLength());
        }
        return new HttpRequest(requestHeader, body);
    }

    private void handleGet(HttpRequest httpRequest, DataOutputStream dos) throws IOException, URISyntaxException {
        String url = httpRequest.getRequestUrl();
        if (url.endsWith(".html")) {
            responseHtml(url, dos);
            return;
        }
        if (url.endsWith(".css")) {
            responseCss(url, dos);
            return;
        }
        if (url.startsWith("/user/create")) {
            String[] splitUrl = url.split("\\?");
            responseRegister(splitUrl[1], dos);
            return;
        }
    }

    private void handlePost(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        String url = httpRequest.getRequestUrl();
        if (url.startsWith("/user/create")) {
            responseRegister(httpRequest.getBody(), dos);
            return;
        }
    }

    private void responseHtml(String url, DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + url);
        response200Header(dos, body.length, "text/html");
        responseBody(dos, body);
    }

    private void responseCss(String url, DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + url.substring(1));
        response200Header(dos, body.length, "text/css");
        responseBody(dos, body);
    }

    private void responseRegister(String params, DataOutputStream dos) {
        RequestParameters requestParameters = RequestParameterParser.parse(params);
        DataBase.addUser(new User(
                requestParameters.get("userId"),
                requestParameters.get("password"),
                requestParameters.get("name"),
                requestParameters.get("email"))
        );
        response302Header(dos, "/index.html");
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            String responseHeader = new ResponseHeader(HttpStatus.OK)
                    .addContentType(contentType)
                    .addContentLength(lengthOfBodyContent)
                    .buildToString();
            dos.writeBytes(responseHeader);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            String responseHeader = new ResponseHeader(HttpStatus.FOUND)
                    .addLocation(location)
                    .buildToString();
            dos.writeBytes(responseHeader);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
