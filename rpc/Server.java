package rpc;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(6666)) {
            System.out.println("Server started...");
            while (true) {
                Socket s = ss.accept();
                serverStub(s);
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void serverStub(Socket s) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        Message rpcRequest = (Message) ois.readObject();
        String method = rpcRequest.getMethod();
        int arg1 = rpcRequest.getArg1(), arg2 = rpcRequest.getArg2();
        int result = remoteProcedure(arg1, arg2, method);
        rpcRequest.setResult(result);
        oos.writeObject(rpcRequest);
        oos.flush();
    }
    private static int remoteProcedure(int arg1, int arg2, String method) {
        switch (method) {
            case "add":
                System.out.println("Received "+ method + " request with args: " + arg1 + ", " + arg2);
                return arg1 + arg2;
            case "sub":
                System.out.println("Received "+ method + " request with args: " + arg1 + ", " + arg2);
                return arg1 - arg2;
            case "mul":
                System.out.println("Received "+ method + " request with args: " + arg1 + ", " + arg2);
                return arg1 * arg2;
            case "div":
                System.out.println("Received "+ method + " request with args: " + arg1 + ", " + arg2);
                if (arg2 == 0) {
                    System.out.println("Division by zero error");
                    return 0;
                }
                return arg1 / arg2;
            default:
                throw new IllegalArgumentException("Invalid method: " + method);
        }
    }

}
