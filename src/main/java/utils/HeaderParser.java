package utils;

import webserver.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;

public class HeaderParser {
    public static RequestHeader parse(BufferedReader br) {
        RequestHeader requestHeader = new RequestHeader();
        parseFirstLine(requestHeader, br);
        parseRemaining(requestHeader, br);
        return requestHeader;
    }

    private static void parseFirstLine(RequestHeader requestHeader, BufferedReader br) {
        try {
            String line = br.readLine();
            if (line == null) return;
            String[] splitLine = line.split(" ");
            requestHeader.put("Method", splitLine[0]);
            requestHeader.put("URL", splitLine[1]);
            requestHeader.put("Protocol", splitLine[2]);
        } catch (IOException e) {
            return;
        }
    }

    private static void parseRemaining(RequestHeader requestHeader, BufferedReader br) {
        try{
            String line = br.readLine();
            while (!"".equals(line)) {
                String[] splitLine = line.split(": ");
                requestHeader.put(splitLine[0], splitLine[1]);
                line = br.readLine();
                if (line == null) return;
            }
        } catch (IOException e) {
            return;
        }
    }
}
