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
                }
            } else if ("POST".equals(httpMethod)) {
                String body = IOUtils.readData(br, Integer.parseInt(headerFields.get("Content-Length")));
                if (path.equals("/user/create")) {
                    DataBase.addUser(User.createUser(body));
                }
            }

            logger.debug("Request Header : {}", requestLine);
            logger.debug("Request Header Url : {}", path);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + path);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
