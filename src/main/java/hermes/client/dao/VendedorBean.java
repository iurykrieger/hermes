package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.vendedor.VendedorRemote;
import hermes.ejb.entidades.Vendedor;
import hermes.ejb.entidades.VendedorInfo;
import hermes.ejb.utils.EjbConstants;
import java.util.List;

/**
 *
 * @author iury
 */
public class VendedorBean extends AbstractBean<VendedorRemote> {

    public VendedorBean() throws Exception {
        lookup(EjbConstants.CONTROLA_VENDEDOR);
    }

    public Vendedor cadastraVendedor(Vendedor vendedor) throws Exception {
        Vendedor m;
        m = ejb.cadastraVendedor(vendedor);
        return m;
    }

    public List<Vendedor> consultaVendedores() throws Exception {
        List<Vendedor> vendedores;
        vendedores = ejb.consultaVendedores();
        return vendedores;
    }

    public VendedorInfo cadastraVendedorInfo(VendedorInfo vendedorinfo) throws Exception {
        VendedorInfo mi;
        mi = ejb.cadastraVendedorInfo(vendedorinfo);
        return mi;
    }

    public List<VendedorInfo> consultaVendedorInfos() throws Exception {
        List<VendedorInfo> vendedores;
        vendedores = ejb.consultaVendedorInfos();
        return vendedores;
    }
}
