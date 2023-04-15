import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.BufferedReader;

public class udpTrafficGen
{
       public static void main(String[] args) {
        int bufferSize = 1024;
        InetAddress serverAddress;
        int serverPort = 7501;

        System.out.println("This program will generate some test traffic for 2 players on the red team as well as 2 players on the green team\n");

        try {
            // Get user input for player IDs
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter ID of red player 1 ==> ");
            String red1 = reader.readLine();
            System.out.print("Enter ID of red player 2 ==> ");
            String red2 = reader.readLine();
            System.out.print("Enter ID of green player 1 ==> ");
            String green1 = reader.readLine();
            System.out.print("Enter ID of green player 2 ==> ");
            String green2 = reader.readLine();
            System.out.print("\nHow many events do you want ==> ");
            int counter = Integer.parseInt(reader.readLine());

            serverAddress = InetAddress.getByName("127.0.0.1");
            DatagramSocket socket = new DatagramSocket();

            // Counter number of events, random player and order
            for (int i = 0; i < counter; i++) {
                String redPlayer, greenPlayer, message;
                if (Math.random() < 0.5) {
                    redPlayer = red1;
                } else {
                    redPlayer = red2;
                }

                if (Math.random() < 0.5) {
                    greenPlayer = green1;
                } else {
                    greenPlayer = green2;
                }

                if (Math.random() < 0.5) {
                    message = redPlayer + ":" + greenPlayer;
                } else {
                    message = greenPlayer + ":" + redPlayer;
                }

                System.out.println(message);
                byte[] data = message.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
                socket.send(packet);
                Thread.sleep((int)(Math.random() * 1000) + 1); // Sleep for random time between 1 to 1000 milliseconds
            }

            socket.close();
            System.out.println("Program complete");
        } catch (IOException e) {
            System.err.println("Error: Failed to read user input");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Error: Thread interrupted");
            e.printStackTrace();
        }
    }
}
