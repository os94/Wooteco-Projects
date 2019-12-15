package nextstep.di.bean.scanner;

import nextstep.di.bean.InitializeBeanException;
import nextstep.di.bean.definition.BeanDefinition;
import nextstep.di.bean.definition.ClasspathBeanDefinition;
import nextstep.di.bean.factory.ClasspathBeanFactory;
import nextstep.stereotype.Controller;
import nextstep.stereotype.Repository;
import nextstep.stereotype.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public class ClasspathBeanScanner implements BeanScanner {
    private static final Logger logger = LoggerFactory.getLogger(ClasspathBeanScanner.class);
    private static final List<Class<? extends Annotation>> SCAN_TARGET_ANNOTATIONS = Arrays.asList(Controller.class, Service.class, Repository.class);

    private ClasspathBeanFactory beanFactory;
    private Reflections reflections;

    public ClasspathBeanScanner(ClasspathBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ClasspathBeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Set<Class<?>> getPreInstantiateClass() {
        return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInstantiateBeans = new HashSet<>();
        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiateBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        logger.debug("Scan Beans Type : {}", preInstantiateBeans);
        return preInstantiateBeans;
    }

    @Override
    public Set<BeanDefinition> scan() {
        return SCAN_TARGET_ANNOTATIONS.stream()
                .map(annotation -> reflections.getTypesAnnotatedWith(annotation))
                .flatMap(Collection::stream)
                .map(ClasspathBeanScanner::newClasspathBeanDefinition)
                .collect(toSet());
    }

    private static ClasspathBeanDefinition newClasspathBeanDefinition(Class<?> clazz) {
        try {
            return new ClasspathBeanDefinition(clazz, clazz.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            logger.error("Error occurred while making ClasspathBeanDefinition.", e);
            throw new InitializeBeanException(e);
        }
    }
}
