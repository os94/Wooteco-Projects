package http;

import java.util.Map;

public class HttpRequest {
    private RequestLine requestLine;
    private HeaderFields headerFields;
    private RequestDatas datas;

    public HttpRequest(RequestLine requestLine, HeaderFields headerFields, RequestDatas datas) {
        this.requestLine = requestLine;
        this.headerFields = headerFields;
        this.datas = datas;
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public String getHeader(String fieldName) {
        return headerFields.getHeader(fieldName);
    }

    public String getData(String fieldName) {
        return datas.getData(fieldName);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getDatas() {
        return datas.getDatas();
    }
}
