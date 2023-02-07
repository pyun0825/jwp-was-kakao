package utils;

import webserver.request.RequestParameters;

public class RequestParameterParser {
    public static RequestParameters parse(String query) {
        RequestParameters requestParameters = new RequestParameters();
        String[] params = query.split("&");
        for (String param : params) {
            String[] splitParam = param.split("=");
            if (splitParam.length != 2) {
                throw new IllegalArgumentException();
            }
            requestParameters.put(splitParam[0], splitParam[1]);
        }
        return requestParameters;
    }
}
