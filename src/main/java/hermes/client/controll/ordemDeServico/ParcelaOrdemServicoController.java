package hermes.client.controll.ordemDeServico;

import hermes.client.dao.ParcelaOrdemServicoBean;
import hermes.ejb.entidades.OrdemDeServico;
import hermes.ejb.entidades.ParcelaOdemDeServico;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class ParcelaOrdemServicoController {

    private ParcelaOrdemServicoBean bean;

    public ParcelaOrdemServicoController() {
        try {
            bean = new ParcelaOrdemServicoBean();
        } catch (Exception ex) {
            Logger.getLogger(OrdemDeServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ParcelaOdemDeServico cadastraOrdemDeServico(ParcelaOdemDeServico pos) {
        ParcelaOdemDeServico parcela = new ParcelaOdemDeServico();
        try {
            parcela = bean.cadastraOrdemDeServico(pos);
        } catch (Exception ex) {
            Logger.getLogger(ParcelaOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parcela;
    }

    public void removeAllParcelasByOrdemDeServico(OrdemDeServico os) {
        try {
            bean.removeAllParcelasByOrdemDeServico(os);
        } catch (Exception ex) {
            Logger.getLogger(ParcelaOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ParcelaOdemDeServico> consultaParcelasByOrdemDeServico(OrdemDeServico os) {
        List<ParcelaOdemDeServico> lista = new ArrayList();
        try {
            lista = bean.consultaParcelasByOrdemDeServico(os);
        } catch (Exception ex) {
            Logger.getLogger(ParcelaOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
