package hermes.client.controll.mecanico;

import hermes.client.dao.MecanicoBean;
import hermes.ejb.entidades.Mecanico;
import hermes.ejb.entidades.MecanicoInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class MecanicoController {

    private MecanicoBean bean;

    public MecanicoController() {
        try {
            bean = new MecanicoBean();
        } catch (Exception ex) {
            Logger.getLogger(MecanicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Mecanico cadastraMecanico(Mecanico cliente) {
        Mecanico m = new Mecanico();
        try {
            m = bean.cadastraMecanico(cliente);
        } catch (Exception ex) {
            Logger.getLogger(MecanicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    public MecanicoInfo consultaMecanicoInfoByMecanico(Mecanico m) {
        MecanicoInfo mi = new MecanicoInfo();
        try {
            mi = bean.consultaMecanicoInfoByMecanico(m);
        } catch (Exception ex) {
            Logger.getLogger(MecanicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mi;
    }

    public MecanicoInfo cadastraMecanicoInfo(MecanicoInfo clienteInfo) {
        MecanicoInfo mi = new MecanicoInfo();
        try {
            mi = bean.cadastraMecanicoInfo(clienteInfo);
        } catch (Exception ex) {
            Logger.getLogger(MecanicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mi;
    }

    public List<MecanicoInfo> consultaMecanicoInfosByIdAtivo() {
        List<MecanicoInfo> mecanicos = null;
        try {
            mecanicos = bean.consultaMecanicoInfosByIdAtivo();
        } catch (Exception ex) {
            Logger.getLogger(MecanicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mecanicos;
    }

    public void setMecanicoInfosInactive(Mecanico m) {
        try {
            bean.setMecanicoInfosInactive(m);
        } catch (Exception ex) {
            Logger.getLogger(MecanicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<List<?>> filtraMecanicos(String s, List<MecanicoInfo> mecanicos) {
        List<List<?>> listas = new ArrayList();
        List<MecanicoInfo> mi = new ArrayList();
        List<Mecanico> m = new ArrayList();
        for (MecanicoInfo tmp : mecanicos) {
            if (tmp.getIdMecanico().getNome().contains(s) || tmp.getIdMecanico().getCpf().contains(s) || tmp.getIdMecanico().getSexo().contains(s)
                    || tmp.getEndereco().contains(s) || tmp.getTelefone().contains(s) || tmp.getCelular().contains(s)
                    || tmp.getEnderecoDescricao().contains(s) || tmp.getEmail().contains(s)
                    || tmp.getCidade().contains(s) || tmp.getEstado().contains(s) || tmp.getCep().contains(s)) {
                mi.add(tmp);
                m.add(tmp.getIdMecanico());
            }
        }
        listas.add(m);
        listas.add(mi);
        return listas;
    }
}
