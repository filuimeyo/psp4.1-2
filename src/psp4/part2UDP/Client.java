package psp4.part2UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class Client {
    public void runClient() throws IOException {
        DatagramSocket s = null;
        try {
            byte[] buf = new byte[512];
            s = new DatagramSocket();
            System.out.println("UDPClient: Started");
            byte[] verCmd = {'V', 'E', 'R', 'S'};
            DatagramPacket sendPacket = new DatagramPacket(verCmd, verCmd.length, InetAddress.getByName("127.0.0.1"), 8001);
            DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);

            while (true){
                try {
                    double x, y, z;
                    System.out.println("Please enter x value ('quite' - to exit)");
                    x = enterDouble();
                    System.out.println("Please enter y value ('quite' - to exit)");
                    y = enterDouble();
                    System.out.println("Please enter z value ('quite' - to exit)");
                    z = enterDouble();

                    String str = String.valueOf(x) + "_" + String.valueOf(y) + "_" + String.valueOf(z);

                    byte[] quitCmd = str.getBytes();
                    sendPacket.setData(quitCmd);
                    sendPacket.setLength(quitCmd.length);
                    s.send(sendPacket);

                } catch (MyException e){
                    byte[] quitCmd = {'Q', 'U', 'I', 'T'};
                    sendPacket.setData(quitCmd);
                    sendPacket.setLength(quitCmd.length);
                    s.send(sendPacket);
                    break;
                }

                s.receive(recvPacket);
                String responce = new String(recvPacket.getData()).trim();
                System.out.println("~server~: " + responce);


            }

            System.out.println("UDPClient: Ended");

        } finally {
            if (s != null) {
                s.close();//закрытие сокета клиента
            }
        }
    }

    public static double enterDouble() throws MyException {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String numString = null;

        while (true) {
            try {
                numString = stdin.readLine();
                return Double.parseDouble(numString);

            } catch (NumberFormatException e) {
                if(numString.equals("quite")) throw new MyException();
                System.out.println("Please enter double value, u entered: " + numString);
                continue;
            } catch (IOException e) {
                System.out.println("U entered nothing");
                continue;
            }
        }
    }


    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.runClient();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static class MyException extends Exception{

    }


}
