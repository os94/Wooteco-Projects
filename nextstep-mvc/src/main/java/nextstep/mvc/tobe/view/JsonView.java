package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.exception.ObjectMapperException;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    private final ObjectMapper objectMapper;

    public JsonView() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * @return value if one data exist in model; json if two or more data exist in model
     * @throws ObjectMapperException
     */
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        if (model.isEmpty()) {
            writer.print("");
            return;
        }
        if (model.size() == 1) {
            writer.print(tryWriteValueAsString(model.values().toArray()[0]));
            return;
        }
        writer.print(tryWriteValueAsString(model));
    }

    private String tryWriteValueAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ObjectMapperException(e);
        }
    }
}
