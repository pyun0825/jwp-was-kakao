package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HeaderParser;
import utils.IOUtils;
import webserver.handler.Handler;
import webserver.handlermapper.RequestHandlerMapping;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final RequestHandlerMapping requestHandlerMapping;

    private Socket connection;

    public RequestHandler(RequestHandlerMapping requestHandlerMapping, Socket connectionSocket) {
        this.requestHandlerMapping = requestHandlerMapping;
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
            Handler handler = requestHandlerMapping.getHandlerForRequest(httpRequest);
            handler.handleRequest(httpRequest, dos);
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
}
