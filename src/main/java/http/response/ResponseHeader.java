package http.response;

import http.common.HeaderFields;

public class ResponseHeader extends HeaderFields {

    public String convert() {
        StringBuilder sb = new StringBuilder();
        for (String field : headerFields.keySet()) {
            sb.append(field).append(COLON).append(BLANK).append(headerFields.get(field)).append(NEWLINE);
        }
        sb.append(cookies.toSetCookieString());
        return sb.toString();
    }
}
