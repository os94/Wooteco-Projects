package webserver;

import db.DataBase;
import http.*;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final String HTML_DEFAULT_PATH = "./templates";
    private static final String STATIC_DEFAULT_PATH = "./static";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestFactory.createHttpRequest(in);
            String path = httpRequest.getPath();

            DataOutputStream dos = new DataOutputStream(out);

            if (path.equals("/user/create")) {
                DataBase.addUser(User.createUser(httpRequest.getDataSet()));
                HttpResponse httpResponse = new HttpResponse(HttpStatus.FOUND);
                sendToClient(dos, httpResponse.sendRedirect("/index.html"));
            }

            if (path.endsWith(".html")) {
                HttpResponse httpResponse = HttpResponseFactory.createHttpResponse(HttpStatus.OK, HTML_DEFAULT_PATH + path);
                sendToClient(dos, httpResponse.forward());
            } else {
                HttpResponse httpResponse = HttpResponseFactory.createHttpResponse(HttpStatus.OK, STATIC_DEFAULT_PATH + path);
                sendToClient(dos, httpResponse.forward());
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendToClient(DataOutputStream dos, String response) throws IOException {
        dos.write(response.getBytes());
        dos.flush();
    }

}
