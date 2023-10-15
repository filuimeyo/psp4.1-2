package psp4.part2UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.List;

public class Server {
    public final static int DEFAULT_PORT = 8001;

    public final String QUIT_CMD = "QUIT";

    public final byte[] UNKNOWN_CMD = {'U', 'n', 'k', 'n', 'o', 'w', 'n', ' ',
            'c', 'o', 'm', 'm', 'a', 'n', 'd'};

    public void runServer() throws IOException {
        DatagramSocket s = null;
        try {
            boolean stopFlag = false;
            byte[] buf = new byte[512];
            s = new DatagramSocket(DEFAULT_PORT);
            System.out.println("UDPServer: Started on " + s.getLocalAddress() + ":"
                    + s.getLocalPort());
            while (!stopFlag) {
                DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);
                s.receive(recvPacket);

                String cmd = new String(recvPacket.getData(), 0, recvPacket.getLength());

                DatagramPacket sendPacket = new DatagramPacket(buf, 0, recvPacket.getAddress(), recvPacket.getPort());                                                                // дейтаграммы для отсылки данных
                int n = 0;

                if (cmd.equals(QUIT_CMD)) {
                    stopFlag = true;
                    continue;
                } else {
                    String[] numsString = cmd.split("_");

                    double x = Double.parseDouble(numsString[0]);
                    double y = Double.parseDouble(numsString[1]);
                    double z = Double.parseDouble(numsString[2]);

                    System.out.println("Received from client: x = " + x + ", y = " + y + ", z = " + z);

                    String result = String.valueOf(countNumbers(x, y, z));
                    System.out.println("Result = " + result);
                    result = "f(x, y, z) = " + result;
                    byte[] quitCmd = result.getBytes();
                    n = quitCmd.length;
                    System.arraycopy(quitCmd, 0, buf, 0, n);
                }
                sendPacket.setData(buf);
                sendPacket.setLength(n);
                s.send(sendPacket);
            }
            System.out.println("UDPServer: Stopped");
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    private double countNumbers(double x, double y, double z) {
        return y + Math.exp(x - y) / (y + Math.pow(x, 2) / (y + Math.pow(x, 3) / y))
                * Math.pow((1 + Math.pow(Math.tan(z / 3), 2)), Math.sqrt(Math.abs(y) + 7));
    }

    public static void main(String[] args) {
        try {
            Server udpSvr = new Server();
            udpSvr.runServer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
