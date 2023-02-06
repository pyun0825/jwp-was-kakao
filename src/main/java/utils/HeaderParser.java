package utils;

import org.springframework.http.HttpMethod;
import webserver.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;

public class HeaderParser {
    public static RequestHeader parse(BufferedReader br) throws IOException {
        RequestHeader requestHeader = parseFirstLine(br);
        parseRemaining(requestHeader, br);
        return requestHeader;
    }

    private static RequestHeader parseFirstLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) throw new RuntimeException();
        String[] splitLine = line.split(" ");
        return new RequestHeader(
                HttpMethod.valueOf(splitLine[0]),
                splitLine[1],
                splitLine[2]);
    }

    private static void parseRemaining(RequestHeader requestHeader, BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!"".equals(line)) {
            String[] splitLine = line.split(": ");
            requestHeader.put(splitLine[0], splitLine[1]);
            line = br.readLine();
            if (line == null) return;
        }
    }
}
