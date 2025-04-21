package dsm;
import java.io.*;
import java.net.*;

public class Server {
    private static int var = 0;
    private static synchronized int get(PrintStream cout) {
        cout.println("Current variable: " + var);
        return var;
    }
    private static synchronized void set(int value, PrintStream cout) {
        var = value;
        cout.println("Updated variable: " + var);
    }
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(6666)) {
            System.out.println("Server started...");
            while (true) {
                Socket s = ss.accept();
                System.out.println("Client connected: " + s.getInetAddress().getHostAddress());
                new Thread(new ClientHandler(s)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class ClientHandler implements Runnable {
        private final Socket s;
        public ClientHandler(Socket s) { this.s = s; }
        @Override
        public void run() {
            try (
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintStream cout = new PrintStream(s.getOutputStream());
            ) {
                String str;
                while ((str = br.readLine()) != null) {
                    if (str.equalsIgnoreCase("get")) get(cout);
                    else if (str.equalsIgnoreCase("set")) {
                        try {
                            int val = Integer.parseInt(br.readLine());
                            set(val, cout);
                        } catch (NumberFormatException e) {
                            cout.println("Invalid Number Format");
                        }
                    } else if (str.equalsIgnoreCase("exit")) {
                        System.out.println("Client disconnected: " + s.getInetAddress().getHostAddress());
                        cout.println("Disconnecting...");
                        break;
                    } else {
                        cout.println("Invalid Command.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }    
    }
}
