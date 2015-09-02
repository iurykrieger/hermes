package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.produto.ItemExternoRemote;
import hermes.ejb.entidades.ItemExternoOrdemServico;
import hermes.ejb.entidades.OrdemDeServico;
import hermes.ejb.utils.EjbConstants;
import java.util.List;

/**
 *
 * @author iury
 */
public class ItemExternoBean extends AbstractBean<ItemExternoRemote> {

    public ItemExternoBean() throws Exception {
        lookup(EjbConstants.CONTROLA_ITEM_EXTERNO);
    }

    public ItemExternoOrdemServico cadastraItemExterno(ItemExternoOrdemServico item) throws Exception {
        ItemExternoOrdemServico i;
        i = ejb.cadastraItemExterno(item);
        return i;
    }

    public List<ItemExternoOrdemServico> consultaItensExternos() throws Exception {
        List<ItemExternoOrdemServico> itens;
        itens = ejb.consultaItensExternos();
        return itens;
    }

    public List<ItemExternoOrdemServico> consultaItensExternosByOrdemServico(OrdemDeServico os) throws Exception {
        List<ItemExternoOrdemServico> itens;
        itens = ejb.consultaItensExternosByOrdemServico(os);
        return itens;
    }
}
