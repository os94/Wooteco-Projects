package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    void name() {
        String a = "A";
        byte[] b = a.getBytes();
        String c = new String(b);
        byte[] d = c.getBytes();
        System.out.println(b.length);
        System.out.println(d.length);
        System.out.println(b.equals(d));
    }
}
