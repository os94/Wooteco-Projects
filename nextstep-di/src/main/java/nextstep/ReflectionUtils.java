package nextstep;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ReflectionUtils {
    public static Constructor<?> getDefaultConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(clazz + " is Interface. There's no Default Constructor."));
    }
}
