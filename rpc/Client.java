package rpc;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.println("Enter method (add, sub, mul, div) or 'exit' to quit:");
                String method = br.readLine();
                if (method.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("Enter first argument:");
                int arg1 = Integer.parseInt(br.readLine());
                System.out.println("Enter second argument:");
                int arg2 = Integer.parseInt(br.readLine());
                int result = localProcedure(arg1, arg2, method);
                System.out.println("Result: " + result);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int localProcedure(int arg1, int arg2, String method) throws Exception {
        return clientStub(arg1, arg2, method);
    }
    private static int clientStub(int arg1, int arg2, String method) throws Exception {
        Socket s = new Socket("localhost", 6666);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        Message rpcRequest = new Message(arg1, arg2, method);
        oos.writeObject(rpcRequest);
        oos.flush();
        Message rpcResponse = (Message) ois.readObject();
        oos.close();
        ois.close();
        s.close();
        return rpcResponse.getResult();
    }
}
