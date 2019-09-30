package webserver.resolver;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.apache.commons.io.FilenameUtils;
import view.ModelAndView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResourceResolver implements Resolver {
    private static final Set<String> TEMPLATE_EXTENSION = new HashSet<>(Arrays.asList(
            "html", "ico"
    ));
    private static final Set<String> STATIC_EXTENSION = new HashSet<>(Arrays.asList(
            "css", "js", "png", "eot", "svg", "ttf", "woff", "woff2"
    ));
    private static final String TEMPLATE_FILE_PATH = "./templates";
    private static final String STATIC_FILE_PATH = "./static";

    @Override
    public ModelAndView resolve(HttpRequest request, HttpResponse response) {
        String path = request.getPath();
        String extension = FilenameUtils.getExtension(path);

        if (STATIC_EXTENSION.contains(extension)) {
            return new ModelAndView(STATIC_FILE_PATH + path, HttpStatus.OK);
        }
        if (TEMPLATE_EXTENSION.contains(extension)) {
            return new ModelAndView(TEMPLATE_FILE_PATH + path, HttpStatus.OK);
        }
        return new ModelAndView("Not Supported File Extension", HttpStatus.NOT_FOUND);
    }
}
