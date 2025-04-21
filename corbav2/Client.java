import CalculatorApp.*;
import java.io.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            Calculator calculatorRef = CalculatorHelper.narrow(ncRef.resolve_str("Calculator"));
            while (true) {
                System.out.println("(add/sub/mul/div)");
                String method = br.readLine();
                System.out.println("Arg 1:");
                int arg1 = Integer.parseInt(br.readLine());
                System.out.println("Arg 2:");
                int arg2 = Integer.parseInt(br.readLine());
                int response = calculatorRef.operation(arg1, arg2, method);
                System.out.println("Response from server: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
