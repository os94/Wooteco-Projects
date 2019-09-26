package http.request;

import http.HttpSession;
import http.common.HeaderFields;
import http.common.HttpMethod;
import http.exception.HttpRequestCreateException;
import http.exception.InvalidHttpHeaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.SessionManager;

import java.util.Arrays;

import static http.common.HeaderFields.BLANK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestTest {
    private RequestLine requestLine;
    private Parameters requestBody;
    private HeaderFields headerFields;

    private HttpRequest request;

    @BeforeEach
    void setUp() {
        requestLine = new RequestLine("GET /index.html?userId=1 HTTP/1.1");
        requestBody = new Parameters("name=sean&password=1234");
        headerFields = new HeaderFields(Arrays.asList(
                "Content-Length: 13309",
                "Content-Type: text/html; charset=utf-8",
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
        ));

        request = new HttpRequest(requestLine, headerFields, requestBody);
    }

    @Test
    void constructor_null_check() {
        requestLine = new RequestLine("GET /index.html?userId=1 HTTP/1.1");
        requestBody = new Parameters("name=sean&password=1234");

        assertThrows(HttpRequestCreateException.class, () -> new HttpRequest(requestLine, null, requestBody));
    }

    @Test
    void request_is_file() {
        assertThat(request.requestFile()).isTrue();
    }

    @Test
    void request_is_not_file() {
        requestLine = new RequestLine("POST /user/create?name=sean&password=1234 HTTP/1.1");
        request = new HttpRequest(requestLine, headerFields, requestBody);
        assertThat(request.requestFile()).isFalse();
    }

    @Test
    void getCookie() {
        headerFields = new HeaderFields(Arrays.asList(
                "Cookie: qwe=123;asd=456",
                "Accept: */*"
        ));
        request = new HttpRequest(requestLine, headerFields, requestBody);

        assertThat(request.getCookie("qwe")).isEqualTo("123");
        assertThat(request.getCookie("asd")).isEqualTo("456");
    }

    @Test
    @DisplayName("getCookie는 Cookie를 갖고있지않으면, 공백문자를 반환")
    void getCookie_return_blank_when_doesnot_have() {
        request = new HttpRequest(requestLine, headerFields, requestBody);
        assertThat(request.getCookie("qwe")).isEqualTo(BLANK);
    }

    @Test
    void getSession() {
        HttpSession session = SessionManager.createSession();
        String jSessionId = session.getId();

        headerFields = new HeaderFields(Arrays.asList(
                "Cookie: JSESSIONID=" + jSessionId + ";test=foo;",
                "Accept: */*"
        ));
        request = new HttpRequest(requestLine, headerFields, requestBody);

        assertThat(request.getSession()).isEqualTo(session);
    }

    @Test
    void getParameter_with_data_in_queryString() {
        assertThat(request.getParameter("userId")).isEqualTo("1");
    }

    @Test
    void getParameter_with_data_in_requestBody() {
        assertThat(request.getParameter("name")).isEqualTo("sean");
    }

    @Test
    void getParameter_with_notExist_data() {
        assertThrows(InvalidHttpHeaderException.class, () -> request.getParameter("foo"));
    }

    @Test
    void getContentTypeByAccept() {
        assertThat(request.getContentTypeByAccept()).isEqualTo("text/html");
    }

    @Test
    void getter() {
        assertThat(request.getMethod()).isEqualByComparingTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(request.getHeader("Content-Type")).isEqualTo("text/html; charset=utf-8");
    }
}