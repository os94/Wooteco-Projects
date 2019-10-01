package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> logger.debug("Question Fields : {}", field));
        Arrays.stream(clazz.getDeclaredConstructors())
                .forEach(constructor -> logger.debug("Question Constructor : {}", constructor));
        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(method -> logger.debug("Question Method : {}", method));
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }

        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
        Constructor<Question> constructor = clazz.getDeclaredConstructor(String.class, String.class, String.class);
        Question question = constructor.newInstance("ethan", "title", "contents");
        assertThat(question).isEqualTo(new Question("ethan", "title", "contents"));
    }

    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        // TODO Student private field에 값을 저장하고 조회한다.
        Field nameField = clazz.getDeclaredField("name");
        Field ageField = clazz.getDeclaredField("age");

        nameField.setAccessible(true);
        ageField.setAccessible(true);

        Student student = new Student();
        nameField.set(student, "ethan");
        ageField.set(student, 10);

        assertThat(student.getName()).isEqualTo("ethan");
        assertThat(student.getAge()).isEqualTo(10);
    }
}
