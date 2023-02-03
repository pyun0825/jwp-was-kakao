package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);

            Headers headers = HeaderParser.parse(br);
            if ("GET".equals(headers.getHttpMethod())) {
                handleGet(headers, dos);
            }
            if ("POST".equals(headers.getHttpMethod())) {
                handlePost(headers, br, dos);
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleGet(Headers headers, DataOutputStream dos) throws IOException, URISyntaxException {
        String url = headers.getUrl();
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

    private void handlePost(Headers headers, BufferedReader br, DataOutputStream dos) throws IOException {
        String url = headers.getUrl();
        if (url.startsWith("/user/create")) {
            responseRegister(IOUtils.readData(br, headers.getContentLength()), dos);
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
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location);
            dos.writeBytes("\r\n");
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
