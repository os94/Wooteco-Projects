package nextstep.di.bean.scanner;

import nextstep.di.bean.definition.BeanDefinition;

import java.util.Set;

public interface BeanScanner {
    Set<BeanDefinition> scan();
}
