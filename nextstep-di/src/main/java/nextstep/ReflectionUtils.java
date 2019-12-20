package nextstep;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ReflectionUtils {
    private static final int ZERO = 0;

    public static Constructor<?> getDefaultConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(ReflectionUtils::isDefaultConstructor)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(clazz + " is Interface. There's no Default Constructor."));
    }

    private static boolean isDefaultConstructor(Constructor<?> constructor) {
        return constructor.getParameterCount() == ZERO;
    }
}
