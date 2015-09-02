package hermes.client.controll.fornecedor;

import hermes.client.dao.FornecedorBean;
import hermes.ejb.entidades.Fornecedor;
import hermes.ejb.entidades.FornecedorInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class FornecedorController {

    private FornecedorBean bean;

    public FornecedorController() {
        bean = new FornecedorBean();
    }

    public Fornecedor cadastraFornecedor(Fornecedor fornecedor) {
        Fornecedor f = new Fornecedor();
        try {
            f = bean.cadastraFornecedor(fornecedor);
        } catch (Exception ex) {
            Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    public FornecedorInfo cadastraFornecedorInfo(FornecedorInfo fornecedorInfo) {
        FornecedorInfo fi = new FornecedorInfo();
        try {
            fi = bean.cadastraFornecedorInfo(fornecedorInfo);
        } catch (Exception ex) {
            Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fi;
    }

    public List<FornecedorInfo> consultaFornecedorInfosByIdAtivo() {
        List<FornecedorInfo> fornecedores = null;
        try {
            fornecedores = bean.consultaFornecedorInfosByIdAtivo();
        } catch (Exception ex) {
            Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fornecedores;
    }

    public void setFornecedorInfosInactive(Fornecedor c) {
        try {
            bean.setFornecedorInfosInactive(c);
        } catch (Exception ex) {
            Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<List<?>> filtraFornecedores(String s, List<FornecedorInfo> fornecedores) {
        List<List<?>> listas = new ArrayList();
        List<FornecedorInfo> fi = new ArrayList();
        List<Fornecedor> f = new ArrayList();
        for (FornecedorInfo tmp : fornecedores) {
            if (tmp.getIdFornecedor().getNome().contains(s) || tmp.getIdFornecedor().getCnpj().contains(s)
                    || tmp.getEndereco().contains(s) || tmp.getTelefone().contains(s)
                    || tmp.getCelular().contains(s) || tmp.getEmail().contains(s)
                    || tmp.getCidade().contains(s) || tmp.getEstado().contains(s) || tmp.getIdFornecedor().getCnpj().contains(s)) {
                fi.add(tmp);
                f.add(tmp.getIdFornecedor());
            }
        }
        listas.add(f);
        listas.add(fi);
        return listas;
    }
}
