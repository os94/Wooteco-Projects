package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResourceResolver implements Resolver {
    private static final Logger logger = LoggerFactory.getLogger(ResourceResolver.class);

    private static final Set<String> TEMPLATE_EXTENSION = new HashSet<>(Arrays.asList(
            "html", "ico"
    ));
    private static final Set<String> STATIC_EXTENSION = new HashSet<>(Arrays.asList(
            "css", "js", "png", "eot", "svg", "ttf", "woff", "woff2"
    ));
    private static final String TEMPLATE_FILE_PATH = "./templates";
    private static final String STATIC_FILE_PATH = "./static";

    @Override
    public void resolve(HttpRequest request, HttpResponse response) {
        String path = request.getPath();
        String extension = FilenameUtils.getExtension(path);

        try {
            if (STATIC_EXTENSION.contains(extension)) {
                path = STATIC_FILE_PATH + path;
                setResponse(response, path);
                return;
            }
            if (TEMPLATE_EXTENSION.contains(extension)) {
                path = TEMPLATE_FILE_PATH + path;
                setResponse(response, path);
                return;
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            response.notFound(e);
        }
        throw new IllegalArgumentException("Not Supported File Extension");
    }

    private void setResponse(HttpResponse response, String path) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        response.ok(body);
    }
}
