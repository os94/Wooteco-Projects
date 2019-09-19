package webserver;

import db.DataBase;
import http.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.HttpRequestFactory;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static http.HttpMethod.*;

public class RequestHandler implements Runnable {
    private static final String HTML_DEFAULT_PATH = "./templates";
    private static final String CSS_DEFAULT_PATH = "./static";
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
            DataOutputStream dos = new DataOutputStream(out);
            String path = httpRequest.getPath();

            if (httpRequest.isGetMethod()) {
                String pathWithoutParams = path.split("\\?")[0];
                if (pathWithoutParams.equals("/user/create")) {
                    DataBase.addUser(User.createUser(httpRequest.getDataSet()));
                    response302Header(dos, "/index.html");
                    return;
                }
            }
            if (httpRequest.isPostMethod()) {
                if (path.equals("/user/create")) {
                    DataBase.addUser(User.createUser(httpRequest.getDataSet()));
                    response302Header(dos, "/index.html");
                    return;
                }
            }

            byte[] body;
            if (path.contains(".css")) {
                body = FileIoUtils.loadFileFromClasspath(CSS_DEFAULT_PATH + path);
                response200Header(dos, body.length,"text/css");
            } else {
                body = FileIoUtils.loadFileFromClasspath(HTML_DEFAULT_PATH + path);
                response200Header(dos, body.length,"text/html");
            }
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
