package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.ordemDeServico.OrdemDeServicoRemote;
import hermes.ejb.entidades.OrdemDeServico;
import hermes.ejb.utils.EjbConstants;
import java.util.List;

/**
 *
 * @author iury
 */
public class OrdemDeServicoBean extends AbstractBean<OrdemDeServicoRemote> {

    public OrdemDeServicoBean() throws Exception {
        lookup(EjbConstants.CONTROLA_ORDEM_DE_SERVICO);
    }

    public OrdemDeServico cadastraOrdemDeServico(OrdemDeServico os) throws Exception {
        OrdemDeServico ods;
        ods = ejb.cadastraOrdemDeServico(os);
        return ods;
    }

    public List<OrdemDeServico> consultaOrdemDeServicos() throws Exception {
        List<OrdemDeServico> os;
        os = ejb.consultaOrdemDeServicos();
        return os;
    }

    public List<OrdemDeServico> consultaOrdemDeServicoByNotPayed() throws Exception {
        List<OrdemDeServico> os;
        os = ejb.consultaOrdemDeServicoByNotPayed();
        return os;
    }
}
