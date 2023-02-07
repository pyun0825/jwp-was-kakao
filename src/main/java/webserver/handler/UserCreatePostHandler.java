package webserver.handler;

import db.DataBase;
import model.User;
import utils.RequestParameterParser;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.RequestParameters;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class UserCreatePostHandler implements Handler{
    @Override
    public void handleRequest(HttpRequest request, DataOutputStream dos) throws IOException, URISyntaxException {
        RequestParameters requestParameters = RequestParameterParser.parse(
                request.getBody().orElseThrow(IllegalArgumentException::new)
        );
        DataBase.addUser(new User(
                requestParameters.get("userId").orElseThrow(IllegalArgumentException::new),
                requestParameters.get("password").orElseThrow(IllegalArgumentException::new),
                requestParameters.get("name").orElseThrow(IllegalArgumentException::new),
                requestParameters.get("email").orElseThrow(IllegalArgumentException::new))
        );
        HttpResponse httpResponse = HttpResponse.found().setLocation("/index.html");
        httpResponse.sendResponse(dos);
    }
}
