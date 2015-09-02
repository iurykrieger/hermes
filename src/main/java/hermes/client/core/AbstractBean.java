package hermes.client.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.naming.InitialContext;

/**
 *
 * @author Flavio
 * @param <K>
 */
public class AbstractBean<K> {

    public K ejb;
    private InitialContext ic;

    public void lookup(String Name) throws Exception {
        ic = Principal.getInInstance().getInitialContext();
        ejb = (K) ic.lookup(Name);
    }
}
