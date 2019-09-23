package http.request;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static http.common.HeaderFields.*;
import static java.util.Collections.unmodifiableMap;

public class RequestDatas {
    private static final Logger logger = LoggerFactory.getLogger(RequestDatas.class);

    private final Map<String, String> datas;

    public RequestDatas(String queryString, String bodyData) {
        datas = new HashedMap<>();
        addDataIfExist(queryString);
        addDataIfExist(bodyData);
    }

    private void addDataIfExist(String data) {
        if (exist(data)) {
            addDatas(Arrays.asList(data.split(AMPERSAND)));
        }
    }

    private boolean exist(String data) {
        return !StringUtils.isEmpty(data);
    }

    private void addDatas(List<String> tokens) {
        tokens.forEach(token -> {
            logger.debug("data : {}", token);
            datas.put(token.split(EQUAL)[0], token.split(EQUAL)[1]);
        });
    }

    public String getData(String name) {
        if (datas.containsKey(name)) {
            return datas.get(name);
        }
        throw new IllegalArgumentException(name + "를 찾을 수 없습니다.");
    }

    public Map<String, String> getDatas() {
        return unmodifiableMap(datas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestDatas that = (RequestDatas) o;
        return Objects.equals(datas, that.datas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datas);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String data : datas.keySet()) {
            sb.append(data).append(COLON).append(BLANK).append(datas.get(data)).append(NEWLINE);
        }
        return sb.toString();
    }
}
