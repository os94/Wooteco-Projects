package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.RequestHeaderUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);

            String requestLine = br.readLine();
            System.out.println("sean: " + requestLine);
            String httpMethod = RequestHeaderUtils.parseHttpMethod(requestLine);
            String path = RequestHeaderUtils.parsePath(requestLine);

            Map<String, String> headerFields = new HashMap<>();
            String line = br.readLine();
            while (!"".equals(line)) {
                String key = line.substring(0, line.indexOf(":"));
                String value = line.substring(line.indexOf(":") + 2);
                headerFields.put(key, value);
                System.out.println("key:" + key + ", value: " + value);
                line = br.readLine();
            }

            if ("GET".equals(httpMethod)) {
                String pathWithoutParams = path.split("\\?")[0];
                if (pathWithoutParams.equals("/user/create")) {
                    DataBase.addUser(User.createUser(path.split("\\?")[1]));
                    response302Header(dos, "/index.html");
                    return;
                }
            } else if ("POST".equals(httpMethod)) {
                String body = IOUtils.readData(br, Integer.parseInt(headerFields.get("Content-Length")));
                if (path.equals("/user/create")) {
                    DataBase.addUser(User.createUser(body));
                    response302Header(dos, "/index.html");
                    return;
                }
            }

            logger.debug("Request Header : {}", requestLine);
            logger.debug("Request Header Url : {}", path);

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
