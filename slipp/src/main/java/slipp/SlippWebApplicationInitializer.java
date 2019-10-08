package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handleradapter.AnnotationHandlerAdapter;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.handleradapter.ManualHandlerAdapter;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Arrays;
import java.util.List;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        List<HandlerMapping> handlerMappings = Arrays.asList(
                new ManualHandlerMapping(),
                new AnnotationHandlerMapping("slipp.controller"));
        List<HandlerAdapter> handlerAdapters = Arrays.asList(
                new ManualHandlerAdapter(),
                new AnnotationHandlerAdapter());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappings, handlerAdapters);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}