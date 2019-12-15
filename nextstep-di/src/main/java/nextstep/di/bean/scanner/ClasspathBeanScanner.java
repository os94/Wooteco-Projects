package nextstep.di.bean.scanner;

import nextstep.di.bean.definition.BeanDefinition;
import nextstep.di.bean.definition.ClasspathBeanDefinition;
import nextstep.stereotype.Controller;
import nextstep.stereotype.Repository;
import nextstep.stereotype.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ClasspathBeanScanner implements BeanScanner {
    private static final Logger logger = LoggerFactory.getLogger(ClasspathBeanScanner.class);
    private static final List<Class<? extends Annotation>> SCAN_TARGET_ANNOTATIONS = Arrays.asList(Controller.class, Service.class, Repository.class);

    private Reflections reflections;

    public ClasspathBeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    @Override
    public Set<BeanDefinition> scan() {
        return SCAN_TARGET_ANNOTATIONS.stream()
                .map(annotation -> reflections.getTypesAnnotatedWith(annotation))
                .flatMap(Collection::stream)
                .map(ClasspathBeanDefinition::new)
                .collect(toSet());
    }
}
