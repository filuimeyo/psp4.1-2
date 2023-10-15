package psp4.part1TCP;

import java.io.*;
import java.net.*;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException  {

        try {

            System.out.println("server connecting....");
            Socket clientSocket = new Socket("127.0.0.1",2525);
            System.out.println("connection established....");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream  inputStream = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Enter flat price to see suitable flat proposition \n\t('quite' − to exit)" +
                    "\n\t('all' − to see all flat propositions)");
            String clientMessage = stdin.readLine();

            while (!clientMessage.equals("quite")){
                outputStream.writeObject(clientMessage);

                Object object = inputStream.readObject();
                if(object instanceof String){
                    System.out.println("~server~: " + object);
                }
                else{
                    List<Flat> listOfMessages = (List<Flat>) object;
                    System.out.println("~server~: Responded with [" + listOfMessages.size() + "] flats propositions");
                    listOfMessages.forEach(System.out::println);
                }


                System.out.println("---------------------------");
                clientMessage = stdin.readLine();
            }
            outputStream.close();
            inputStream.close();
            clientSocket.close();

        }
        catch (EOFException exc) {
            System.out.println("ajajanj");
        }
        catch (ClassNotFoundException e) {
            System.out.println("The title list has not come from the server");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
