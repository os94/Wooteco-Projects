package view.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import view.ModelAndView;
import view.ViewRenderingException;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceFileStrategy implements ResponseBodyStrategy {
    private static final Logger logger = LoggerFactory.getLogger(ResourceFileStrategy.class);

    @Override
    public byte[] render(ModelAndView modelAndView) {
        try {
            return FileIoUtils.loadFileFromClasspath(modelAndView.getViewName());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            throw new ViewRenderingException(modelAndView.getViewName() + " Rendering Error", e);
        }
    }
}
