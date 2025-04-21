import HelloApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

class HelloImpl extends HelloPOA {
    public ORB orb;
    public void setORB (ORB orb) { this.orb = orb; }
    @Override
    public String sayHello() { return "Hello, CORBA!"; }
}

public class Server {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPoa.the_POAManager().activate();
            HelloImpl helloImpl = new HelloImpl();
            helloImpl.setORB(orb);
            org.omg.CORBA.Object ref = rootPoa.servant_to_reference(helloImpl);
            Hello helloRef = HelloHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NameComponent[] path = ncRef.to_name("Hello");
            ncRef.rebind(path, helloRef);
            System.out.println("Server ready and waiting...");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
