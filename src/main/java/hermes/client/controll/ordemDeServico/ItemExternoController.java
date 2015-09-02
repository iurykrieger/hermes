package hermes.client.controll.ordemDeServico;

import hermes.client.controll.produto.ProdutoController;
import hermes.client.dao.ItemExternoBean;
import hermes.ejb.entidades.ItemExternoOrdemServico;
import hermes.ejb.entidades.OrdemDeServico;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class ItemExternoController {

    private ItemExternoBean bean;

    public ItemExternoController() {
        try {
            bean = new ItemExternoBean();
        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ItemExternoOrdemServico cadastraItemExterno(ItemExternoOrdemServico item) {
        ItemExternoOrdemServico i = new ItemExternoOrdemServico();
        try {
            i = bean.cadastraItemExterno(item);
        } catch (Exception ex) {
            Logger.getLogger(ItemExternoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public List<ItemExternoOrdemServico> consultaItensExternos() {
        List<ItemExternoOrdemServico> itens = new ArrayList();
        try {
            itens = bean.consultaItensExternos();
        } catch (Exception ex) {
            Logger.getLogger(ItemExternoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itens;
    }

    public List<ItemExternoOrdemServico> consultaItensExternosByOrdemServico(OrdemDeServico os) {
        List<ItemExternoOrdemServico> itens = new ArrayList();
        try {
            itens = bean.consultaItensExternosByOrdemServico(os);
        } catch (Exception ex) {
            Logger.getLogger(ItemExternoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itens;
    }
}
