package http.request;

import http.common.HttpMethod;
import http.common.HttpSession;
import http.exception.HttpRequestCreateException;
import http.exception.InvalidHttpHeaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.SessionManager;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestTest {
    private RequestLine requestLine;
    private Parameters requestBody;
    private RequestHeader headerFields;

    private HttpRequest request;

    @BeforeEach
    void setUp() {
        requestLine = new RequestLine("GET /index.html?userId=1 HTTP/1.1");
        requestBody = new Parameters("name=sean&password=1234");
        headerFields = new RequestHeader(Arrays.asList(
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
    void check_session_attribute() {
        request.getSession(true).setAttribute("logined", true);
        assertThat(request.checkSessionAttribute("logined", true)).isTrue();
    }

    @Test
    void check_HttpMethod() {
        assertThat(request.isGetMethod()).isTrue();
        assertThat(request.isPostMethod()).isFalse();
    }

    @Test
    void getCookie() {
        headerFields = new RequestHeader(Arrays.asList(
                "Cookie: qwe=123;asd=456",
                "Accept: */*"
        ));
        request = new HttpRequest(requestLine, headerFields, requestBody);

        assertThat(request.getCookie("qwe")).isEqualTo("123");
        assertThat(request.getCookie("asd")).isEqualTo("456");
    }

    @Test
    @DisplayName("세션이 존재하는 경우, 해당 세션을 반환")
    void getSession_case1() {
        HttpSession session = SessionManager.getInstance().createSession();
        String jSessionId = session.getId();
        headerFields = new RequestHeader(Arrays.asList(
                "Cookie: JSESSIONID=" + jSessionId + ";test=foo;",
                "Accept: */*"
        ));
        request = new HttpRequest(requestLine, headerFields, requestBody);

        assertThat(request.getSession(false)).isEqualTo(session);
    }

    @Test
    @DisplayName("세션이 존재하는 않는 경우, 새로운 세션을 반환")
    void getSession_case2() {
        assertThat(request.getSession(true)).isNotNull();
    }

    @Test
    @DisplayName("세션이 존재하는 않는 경우, null을 반환")
    void getSession_case3() {
        assertThat(request.getSession(false)).isNull();
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