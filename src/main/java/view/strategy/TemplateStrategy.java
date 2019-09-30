package view.strategy;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import view.ViewRenderingException;

import java.io.IOException;

public class TemplateStrategy implements ResponseBodyStrategy {
    private static final Logger logger = LoggerFactory.getLogger(TemplateStrategy.class);

    @Override
    public byte[] render(ModelAndView modelAndView) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile(modelAndView.getViewName());
            return template.apply(modelAndView.getModels()).getBytes();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ViewRenderingException(modelAndView.getViewName() + " Rendering Error", e);
        }
    }
}
