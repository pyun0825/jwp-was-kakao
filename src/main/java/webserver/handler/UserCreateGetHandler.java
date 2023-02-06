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

public class UserCreateGetHandler implements Handler{
    @Override
    public void handleRequest(HttpRequest request, DataOutputStream dos) throws IOException, URISyntaxException {
        RequestParameters requestParameters = RequestParameterParser.parse(request.getRequestQuery());
        DataBase.addUser(new User(
                requestParameters.get("userId"),
                requestParameters.get("password"),
                requestParameters.get("name"),
                requestParameters.get("email"))
        );
        HttpResponse httpResponse = HttpResponse.found().setLocation("/index.html");
        httpResponse.sendResponse(dos);
    }
}
