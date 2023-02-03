package utils;

import webserver.Headers;

import java.io.BufferedReader;
import java.io.IOException;

public class HeaderParser {
    public static Headers parse(BufferedReader br) {
        Headers headers = new Headers();
        parseFirstLine(headers, br);
        parseRemaining(headers, br);
        return headers;
    }

    private static void parseFirstLine(Headers headers, BufferedReader br) {
        try {
            String line = br.readLine();
            if (line == null) return;
            String[] splitLine = line.split(" ");
            headers.put("Method", splitLine[0]);
            headers.put("URL", splitLine[1]);
            headers.put("Protocol", splitLine[2]);
        } catch (IOException e) {
            return;
        }
    }

    private static void parseRemaining(Headers headers, BufferedReader br) {
        try{
            String line = br.readLine();
            while (!"".equals(line)) {
                String[] splitLine = line.split(": ");
                headers.put(splitLine[0], splitLine[1]);
                line = br.readLine();
                if (line == null) return;
            }
        } catch (IOException e) {
            return;
        }
    }
}
