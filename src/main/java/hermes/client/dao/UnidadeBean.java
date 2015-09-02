package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.unidade.UnidadeRemote;
import hermes.ejb.entidades.Unidade;
import hermes.ejb.utils.EjbConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class UnidadeBean extends AbstractBean<UnidadeRemote> {

    public UnidadeBean() {
        try {
            lookup(EjbConstants.CONTROLA_UNIDADE);
        } catch (Exception ex) {
            Logger.getLogger(UnidadeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Unidade cadastraUnidade(Unidade unidade) {
        Unidade u = new Unidade();
        try {
            u = ejb.cadastraUnidade(unidade);
        } catch (Exception ex) {
            Logger.getLogger(UnidadeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    public List<Unidade> consultaUnidade() {
        List<Unidade> unidades = new ArrayList();
        try {
            unidades = ejb.consultaUnidades();
        } catch (Exception ex) {
            Logger.getLogger(UnidadeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unidades;
    }
}
