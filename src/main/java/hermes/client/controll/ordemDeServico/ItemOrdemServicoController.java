package hermes.client.controll.ordemDeServico;

import hermes.client.controll.produto.ProdutoController;
import hermes.client.dao.ItemOrdemServicoBean;
import hermes.ejb.entidades.ItemOrdemServico;
import hermes.ejb.entidades.OrdemDeServico;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class ItemOrdemServicoController {

    private ItemOrdemServicoBean bean;

    public ItemOrdemServicoController() {
        try {
            bean = new ItemOrdemServicoBean();
        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ItemOrdemServico cadastraItemOrdemServico(ItemOrdemServico item) {
        ItemOrdemServico i = new ItemOrdemServico();
        try {
            i = bean.cadastraItemOrdemServico(item);
        } catch (Exception ex) {
            Logger.getLogger(ItemOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public List<ItemOrdemServico> consultaItensOrdemServico() {
        List<ItemOrdemServico> itens = new ArrayList();
        try {
            itens = bean.consultaItensOrdemServico();
        } catch (Exception ex) {
            Logger.getLogger(ItemOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itens;
    }

    public List<ItemOrdemServico> consultaItensOrdemServicoByOrdemServico(OrdemDeServico os) {
        List<ItemOrdemServico> itens = new ArrayList();
        try {
            itens = bean.consultaItensOrdemServicoByOrdemServico(os);
        } catch (Exception ex) {
            Logger.getLogger(ItemOrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itens;
    }
}
