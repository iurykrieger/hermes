package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.fornecedor.FornecedorRemote;
import hermes.ejb.entidades.Fornecedor;
import hermes.ejb.entidades.FornecedorInfo;
import hermes.ejb.utils.EjbConstants;
import static java.io.ObjectStreamClass.lookup;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.glassfish.internal.embedded.ContainerBuilder.Type.ejb;

/**
 *
 * @author iury
 */
public class FornecedorBean extends AbstractBean<FornecedorRemote> {

    public FornecedorBean() {
        try {
            lookup(EjbConstants.CONTROLA_FORNECEDOR);
        } catch (Exception ex) {
            Logger.getLogger(FornecedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Fornecedor cadastraFornecedor(Fornecedor fornecedor) throws Exception {
        Fornecedor f;
        f = ejb.cadastraFornecedor(fornecedor);
        return f;
    }

    public List<Fornecedor> consultaFornecedor() throws Exception {
        List<Fornecedor> fornecedores;
        fornecedores = ejb.consultaFornecedores();
        return fornecedores;
    }

    public FornecedorInfo cadastraFornecedorInfo(FornecedorInfo fornecedorinfo) throws Exception {
        FornecedorInfo f;
        f = ejb.cadastraFornecedorInfo(fornecedorinfo);
        return f;
    }

    public List<FornecedorInfo> consultaFornecedorInfos() throws Exception {
        List<FornecedorInfo> fornecedores;
        fornecedores = ejb.consultaFornecedorInfos();
        return fornecedores;
    }

    public List<FornecedorInfo> consultaFornecedorInfosByIdAtivo() throws Exception {
        List<FornecedorInfo> fornecedores;
        fornecedores = ejb.consultaFornecedorInfoByIdAtivo();
        return fornecedores;
    }

    public void setFornecedorInfosInactive(Fornecedor c) throws Exception {
        ejb.setFornecedorInfosInactive(c);
    }
}
