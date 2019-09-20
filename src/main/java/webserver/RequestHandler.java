package webserver;

import http.HttpRequest;
import http.HttpRequestFactory;
import http.HttpResponse;
import http.controller.Controller;
import http.controller.CreateUserController;
import http.controller.ResourcesController;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final Map<String, Controller> controllers = new HashedMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequestFactory.createHttpRequest(in);
            HttpResponse response = new HttpResponse();
            DataOutputStream dos = new DataOutputStream(out);

            Controller controller = controllers.getOrDefault(request.getPath(), new ResourcesController());
            controller.service(request, response);
            dos.write(response.convert().getBytes());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
