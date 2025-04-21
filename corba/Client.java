import HelloApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            Hello helloRef = HelloHelper.narrow(ncRef.resolve_str("Hello"));
            String response = helloRef.sayHello();
            System.out.println("Response from server: " + response);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
