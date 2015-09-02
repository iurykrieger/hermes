package hermes.client.controll.utils;

import hermes.client.dao.EstadosBean;
import com.hermes.components.HMSelections.HMComboBox;
import hermes.ejb.entidades.Cidades;
import hermes.ejb.entidades.Estados;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class EstadosController {

    private List<Estados> estados;
    private List<Cidades> cidades;
    private EstadosBean bean;

    public EstadosController() {
        estados = new ArrayList();
        cidades = new ArrayList();
        try {
            bean = new EstadosBean();
        } catch (Exception ex) {
            Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EstadosController(List<Estados> estados, List<Cidades> cidades) {
        this.estados = estados;
        this.cidades = cidades;
        try {
            bean = new EstadosBean();
        } catch (Exception ex) {
            Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Estados> getEstados() {
        try {
            estados = bean.consultaEstados();
        } catch (Exception ex) {
            Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.estados;
    }

    public List<Cidades> getCidades() throws Exception {
        try {
            cidades = bean.consultaCidades();
        } catch (Exception ex) {
            Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.cidades;
    }

    public List<Cidades> getCidadesByEstado(Estados estado) {
        List<Cidades> cidades = new ArrayList();
        try {
            cidades = bean.consultaCidadesByEstado(estado);
        } catch (Exception ex) {
            Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cidades;
    }

}
