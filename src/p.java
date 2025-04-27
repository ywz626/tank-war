import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author 于汶泽
 */
public class p {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        socket.getOutputStream().write("hello".getBytes());
        socket.shutdownOutput();
        int readLen = 0;
        byte[] buf = new byte[1024];
        while ((readLen = socket.getInputStream().read(buf)) != -1) {
            System.out.println(new String(buf, 0, readLen));
        }
        socket.getOutputStream().close();
        socket.close();
        System.out.println("客户端" + socket.getClass());
        int i = buf.length;
    }
}
