package psp4.part1TCP;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

//Разработать приложение по поиску квартиры для покупки. Стоимости
//квартир и их адреса хранятся на сервере. На клиентской части вводится
//предельная сумма для покупки квартиры, а сервер возвращает клиенту адреса всех
//квартир с такой или меньшей стоимостью.

public class Server {
    public static void main(String[] arg) throws IOException {

        List<Flat> flatList = createFlatList();

        ServerSocket serverSocket = null;
        Socket clientAccepted = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            System.out.println("server starting....");
            serverSocket = new ServerSocket(2525);
            clientAccepted = serverSocket.accept();
            System.out.println("connection established....");
            inputStream = new ObjectInputStream(clientAccepted.getInputStream());
            outputStream = new ObjectOutputStream(clientAccepted.getOutputStream());
            String clientMessageRecieved = (String) inputStream.readObject();


            while (!clientMessageRecieved.equals("quite")) {
                try {
                    double price = Double.parseDouble(clientMessageRecieved);
                    System.out.println("price recieved: " + price);

                    List<Flat> filteredList = flatList.stream()
                            .filter(flat -> flat.getPrice() <= price)
                            .toList();

                    outputStream.writeObject(filteredList);

                } catch (NumberFormatException e) {

                    if (clientMessageRecieved.equals("all")) {
                        System.out.println("Message recieved: " + clientMessageRecieved);
                        outputStream.writeObject(flatList);
                    } else {
                        String sb1 = String.valueOf(Character.toChars(0x1F970));
                        String sb2 = String.valueOf(Character.toChars(0x1F52A));

                        System.out.println("Message recieved: " + clientMessageRecieved);
                        outputStream.writeObject("please enter numbers" + sb1 + sb2);
                    }


                }

                clientMessageRecieved = (String) inputStream.readObject();
            }
        } catch (Exception e) {
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                clientAccepted.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public static List<Flat> createFlatList() {
        List<Flat> flatList = new ArrayList<>();

        Flat flat1 = new Flat(200000, "Pushkina", 33);
        Flat flat2 = new Flat(200001, "Pushkina", 34);
        Flat flat3 = new Flat(200002, "Pushkina", 35);
        Flat flat4 = new Flat(200003, "Pushkina", 36);
        Flat flat5 = new Flat(20000566, "Pushkina", 37);
        Flat flat6 = new Flat(200007, "Pushkina", 38);

        flatList.add(flat1);
        flatList.add(flat2);
        flatList.add(flat3);
        flatList.add(flat4);
        flatList.add(flat5);
        flatList.add(flat6);

        return flatList;
    }
}

