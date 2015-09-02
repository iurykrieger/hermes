package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.ordemDeServico.ParcelaOrdemServicoRemote;
import hermes.ejb.entidades.OrdemDeServico;
import hermes.ejb.entidades.ParcelaOdemDeServico;
import hermes.ejb.utils.EjbConstants;
import java.util.List;

/**
 *
 * @author iury
 */
public class ParcelaOrdemServicoBean extends AbstractBean<ParcelaOrdemServicoRemote> {

    public ParcelaOrdemServicoBean() throws Exception {
        lookup(EjbConstants.CONTROLA_PARCELA_ORDEM_SERVICO);
    }

    public ParcelaOdemDeServico cadastraOrdemDeServico(ParcelaOdemDeServico pos) throws Exception {
        ParcelaOdemDeServico parcela;
        parcela = ejb.cadastraOrdemDeServico(pos);
        return parcela;
    }

    public List<ParcelaOdemDeServico> consultaParcelasByOrdemDeServico(OrdemDeServico os) throws Exception {
        List<ParcelaOdemDeServico> lista;
        lista = ejb.consultaParcelasByOrdemDeServico(os);
        return lista;
    }

    public void removeAllParcelasByOrdemDeServico(OrdemDeServico os) throws Exception {
        ejb.removeAllParcelasByOrdemDeServico(os);
    }
}
