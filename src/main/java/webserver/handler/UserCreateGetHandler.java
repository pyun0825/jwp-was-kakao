package webserver.handler;

import db.DataBase;
import model.User;
import utils.RequestParameterParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.request.RequestParameters;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class UserCreateGetHandler implements Handler{
    @Override
    public void handleRequest(HttpRequest request, DataOutputStream dos) throws IOException, URISyntaxException {
        RequestParameters requestParameters = RequestParameterParser.parse(
                request.getRequestQuery()
                        .orElseThrow(IllegalArgumentException::new)
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
