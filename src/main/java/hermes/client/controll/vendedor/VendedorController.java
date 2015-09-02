/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.controll.vendedor;

import hermes.client.dao.VendedorBean;
import hermes.ejb.entidades.Vendedor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.glassfish.internal.embedded.ContainerBuilder.Type.ejb;

/**
 *
 * @author iury
 */
public class VendedorController {

    private VendedorBean bean;

    public VendedorController() {
        try {
            bean = new VendedorBean();
        } catch (Exception ex) {
            Logger.getLogger(VendedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Vendedor> consultaVendedores() {
        List<Vendedor> vendedores = null;
        try {
            vendedores = bean.consultaVendedores();
        } catch (Exception ex) {
            Logger.getLogger(VendedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vendedores;
    }
}
