import CalculatorApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

class CalculatorImpl extends CalculatorPOA {
    public ORB orb;
    public void setORB (ORB orb) { this.orb = orb; }
    @Override
    public int operation (int a, int b, String method) {
        switch (method) {
            case "add":
                return a + b;
            case "sub":
                return a - b;
            case "mul":
                return a * b;
            case "div":
                if (b == 0) {
                    return 0;
                }
                return a / b;
            default:
                return 0;
        }
    }
}

public class Server {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            CalculatorImpl calculatorImpl = new CalculatorImpl();
            calculatorImpl.setORB(orb);
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(calculatorImpl);
            Calculator calculatorRef = CalculatorHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NameComponent[] path = ncRef.to_name("Calculator");
            ncRef.rebind(path, calculatorRef);
            System.out.println("Server is ready...");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
