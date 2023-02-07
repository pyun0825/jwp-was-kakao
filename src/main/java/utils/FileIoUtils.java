package utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) throw new IllegalArgumentException();
        URI uri = resource.toURI();
        Path path = Paths.get(uri);
        return Files.readAllBytes(path);
    }
}
