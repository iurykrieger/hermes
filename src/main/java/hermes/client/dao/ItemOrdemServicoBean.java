package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.produto.ItemOrdemServicoRemote;
import hermes.ejb.entidades.ItemOrdemServico;
import hermes.ejb.entidades.OrdemDeServico;
import hermes.ejb.utils.EjbConstants;
import java.util.List;

/**
 *
 * @author iury
 */
public class ItemOrdemServicoBean extends AbstractBean<ItemOrdemServicoRemote> {

    public ItemOrdemServicoBean() throws Exception {
        lookup(EjbConstants.CONTROLA_ITEM_ORDEM_SERVICO);
    }

    public ItemOrdemServico cadastraItemOrdemServico(ItemOrdemServico item) throws Exception {
        ItemOrdemServico i;
        i = ejb.cadastraItemOrdemServico(item);
        return i;
    }

    public List<ItemOrdemServico> consultaItensOrdemServico() throws Exception {
        List<ItemOrdemServico> itens;
        itens = ejb.consultaItensOrdemServico();
        return itens;
    }

    public List<ItemOrdemServico> consultaItensOrdemServicoByOrdemServico(OrdemDeServico os) throws Exception {
        List<ItemOrdemServico> itens;
        itens = ejb.consultaItensOrdemServicoByOrdemServico(os);
        return itens;
    }
}
