package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String HTML_DEFAULT_PATH = "./templates";
    private static final String STATIC_DEFAULT_PATH = "./static";

    public static byte[] loadFileFromClasspath(String path) throws IOException, URISyntaxException {
        Path filePath = Paths.get(FileIoUtils.class.getClassLoader().getResource(getFilePath(path)).toURI());
        return Files.readAllBytes(filePath);
    }

    private static String getFilePath(String path) {
        if (path.endsWith(".html")) {
            return HTML_DEFAULT_PATH + path;
        }
        return STATIC_DEFAULT_PATH + path;
    }
}
