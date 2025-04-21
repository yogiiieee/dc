package rmi;
import java.io.*;
import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            Calculator calculator = (Calculator) Naming.lookup("//localhost/Calculator");
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
                switch (method) {
                    case "add":
                        System.out.println("Result: " + calculator.add(arg1, arg2));
                        break;
                    case "sub":
                        System.out.println("Result: " + calculator.sub(arg1, arg2));
                        break;
                    case "mul":
                        System.out.println("Result: " + calculator.mul(arg1, arg2));
                        break;
                    case "div":
                        System.out.println("Result: " + calculator.div(arg1, arg2));
                        break;
                    default:
                        System.out.println("Invalid method. Please try again.");
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
