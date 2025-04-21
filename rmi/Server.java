package rmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements Calculator{
    public Server() throws RemoteException { super(); }
    public int add(int a, int b) throws RemoteException {
        System.out.println("Received add request with args: " + a + ", " + b);
        return a + b;
    }
    public int sub(int a, int b) throws RemoteException {
        System.out.println("Received sub request with args: " + a + ", " + b);
        return a - b;
    }
    public int mul(int a, int b) throws RemoteException {
        System.out.println("Received mul request with args: " + a + ", " + b);
        return a * b;
    }
    public int div(int a, int b) throws RemoteException {
        System.out.println("Received div request with args: " + a + ", " + b);
        if (b == 0) {
            System.out.println("Division by zero error");
            return 0;
        }
        return a / b;
    }
    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            Server server = new Server();
            java.rmi.Naming.rebind("Calculator", server);
            System.out.println("Server started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
