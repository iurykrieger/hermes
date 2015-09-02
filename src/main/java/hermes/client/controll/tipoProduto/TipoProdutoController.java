/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.controll.tipoProduto;

import hermes.client.dao.TipoProdutoBean;
import hermes.ejb.entidades.TipoProduto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class TipoProdutoController {

    private TipoProdutoBean bean;

    public TipoProdutoController() {
        try {
            bean = new TipoProdutoBean();
        } catch (Exception ex) {
            Logger.getLogger(TipoProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<TipoProduto> getTipos() {
        List<TipoProduto> tipos = new ArrayList();
        try {
            tipos = bean.consultaTipoProdutos();
        } catch (Exception ex) {
            Logger.getLogger(TipoProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipos;
    }
}
