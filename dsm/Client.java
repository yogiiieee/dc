package dsm;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (
            Socket s = new Socket("localhost", 6666);
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream cout = new PrintStream(s.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println("Connected to server...");
            while (true) {
                System.out.println("Enter command (get, set, exit");
                String command = input.readLine();
                if (command.equalsIgnoreCase("get")) {
                    cout.println("get");
                    String response = br.readLine();
                    System.out.println("Server response: " + response);
                } else if (command.equalsIgnoreCase("set")) {
                    cout.println("set");
                    System.out.print("Enter value to set: ");
                    int value = Integer.parseInt(input.readLine());
                    cout.println(value);
                    String response = br.readLine();
                    System.out.println("Server response: " + response);
                } else if (command.equalsIgnoreCase("exit")) {
                    cout.println("exit");
                    System.out.println("Disconnecting from server...");
                    break;
                } else {
                    cout.println(command);
                    System.out.println(br.readLine());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
