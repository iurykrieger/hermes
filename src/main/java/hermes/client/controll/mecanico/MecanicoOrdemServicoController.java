package hermes.client.controll.mecanico;

import hermes.client.dao.MecanicoOrdemServicoBean;
import hermes.ejb.entidades.MecanicoOrdemServico;
import hermes.ejb.entidades.OrdemDeServico;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class MecanicoOrdemServicoController {

    private MecanicoOrdemServicoBean bean;

    public MecanicoOrdemServicoController() {
        try {
            bean = new MecanicoOrdemServicoBean();
        } catch (Exception ex) {
            Logger.getLogger(MecanicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MecanicoOrdemServico cadastraMecanicoOrdemServico(MecanicoOrdemServico mecanico) {
        MecanicoOrdemServico m = new MecanicoOrdemServico();
        try {
            m = bean.cadastraMecanicoOrdemServico(mecanico);
        } catch (Exception ex) {
            Logger.getLogger(MecanicoOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    public List<MecanicoOrdemServico> consultaMecanicosOrdemServico() {
        List<MecanicoOrdemServico> mecanicos = new ArrayList();
        try {
            mecanicos = bean.consultaMecanicosOrdemServico();
        } catch (Exception ex) {
            Logger.getLogger(MecanicoOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mecanicos;
    }

    public List<MecanicoOrdemServico> consultaMecanicosOrdemServicoByOrdemServico(OrdemDeServico os) {
        List<MecanicoOrdemServico> mecanicos = new ArrayList();
        try {
            mecanicos = bean.consultaMecanicosOrdemServicoByOrdemServico(os);
        } catch (Exception ex) {
            Logger.getLogger(MecanicoOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mecanicos;
    }
}
