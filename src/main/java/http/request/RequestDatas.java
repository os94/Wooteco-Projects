package http.request;

import http.exception.InvalidRequestHeaderException;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class RequestDatas {
    private static final Logger logger = LoggerFactory.getLogger(RequestDatas.class);

    private Map<String, String> datas;

    public RequestDatas(String queryString, String bodyData) {
        datas = new HashedMap<>();
        addDataIfExist(queryString);
        addDataIfExist(bodyData);
    }

    private void addDataIfExist(String data) {
        if (exist(data)) {
            addDatas(Arrays.asList(data.split("&")));
        }
    }

    private boolean exist(String data) {
        return !StringUtils.isEmpty(data);
    }

    private void addDatas(List<String> tokens) {
        tokens.forEach(token -> {
            logger.debug("data : {}", token);
            datas.put(token.split("=")[0], token.split("=")[1]);
        });
    }

    public String getData(String fieldName) {
        if (datas.containsKey(fieldName)) {
            return datas.get(fieldName);
        }
        throw new InvalidRequestHeaderException(fieldName + "를 찾을 수 없습니다.");
    }

    public Map<String, String> getDatas() {
        return unmodifiableMap(datas);
    }
}
