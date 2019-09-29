package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    public String render(ModelAndView modelAndView) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile(modelAndView.getViewName());
            return template.apply(modelAndView.getModels());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ViewRenderingException(modelAndView.getViewName() + "Rendering Error", e);
        }
    }
}
