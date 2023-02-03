package utils;

import webserver.RequestParameters;

public class RequestParameterParser {
    public static RequestParameters parse(String url) {
        RequestParameters requestParameters = new RequestParameters();
        String[] splitUrl = url.split("\\?");
        if (splitUrl.length != 2) {
            return null;
        }
        String[] params = splitUrl[1].split("&");
        for (String param : params) {
            String[] splitParam = param.split("=");
            if (splitParam.length != 2) {
                return null;
            }
            requestParameters.put(splitParam[0], splitParam[1]);
        }
        return requestParameters;
    }
}
