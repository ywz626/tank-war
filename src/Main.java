import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    //服务端
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println(1);
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        byte[] bytes = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, readLen));
        }
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write(("ywz is the first".getBytes()));
        accept.shutdownOutput();
        accept.close();
        System.out.println(2);
    }
}