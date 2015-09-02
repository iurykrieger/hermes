package hermes.client.controll.unidade;

import hermes.client.dao.UnidadeBean;
import hermes.ejb.entidades.Unidade;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class UnidadeController {

    private UnidadeBean bean;

    public UnidadeController() {
        try {
            bean = new UnidadeBean();
        } catch (Exception ex) {
            Logger.getLogger(UnidadeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Unidade> getUnidades() {
        List<Unidade> unidades = new ArrayList();
        try {
            unidades = bean.consultaUnidade();
        } catch (Exception ex) {
            Logger.getLogger(UnidadeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unidades;
    }
}
