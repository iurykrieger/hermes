/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.core;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author iury
 */
public class Principal {

    private static final Principal Instancia = new Principal();
    private InitialContext ic;

    public InitialContext getInitialContext() throws NamingException {
        Properties props = new Properties();
//   
//            props.setProperty("java.naming.factory.initial",
//                    "com.sun.enterprise.naming.SerialInitContextFactory");
//            props.setProperty("java.naming.factory.url.pkgs",
//                    "com.sun.enterprise.naming");
//            props.setProperty("java.naming.factory.state",
//                    "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        //props.setProperty(InitialContext.PROVIDER_URL, "iiop://192.168.2.100:3700"); 
        props.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.2.100");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        return new InitialContext(props);
    }

    public static Principal getInInstance() {
        return Instancia;
    }

    public Principal() {
    }

}
