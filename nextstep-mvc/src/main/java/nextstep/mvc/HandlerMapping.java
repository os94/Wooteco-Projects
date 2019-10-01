package nextstep.mvc;

public interface HandlerMapping {
    void initialize();

    Object getHandler(String requestUri);
}
