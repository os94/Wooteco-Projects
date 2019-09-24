package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String path) throws IOException, URISyntaxException {
        Path filePath = Paths.get(FileIoUtils.class.getClassLoader().getResource(path).toURI());
        return Files.readAllBytes(filePath);
    }
}
