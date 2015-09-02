/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.tipoProduto.TipoProdutoRemote;
import hermes.ejb.utils.EjbConstants;
import hermes.ejb.entidades.TipoProduto;
import java.util.List;

/**
 *
 * @author iury
 */
public class TipoProdutoBean extends AbstractBean<TipoProdutoRemote> {

    public TipoProdutoBean() throws Exception {
        lookup(EjbConstants.CONTROLA_TIPO_PRODUTO);
    }

    public TipoProduto cadastraTipoProduto(TipoProduto tipoproduto) throws Exception {
        TipoProduto tp;
        tp = ejb.cadastraTipoProduto(tipoproduto);
        return tp;
    }

    public List<TipoProduto> consultaTipoProdutos() throws Exception {
        List<TipoProduto> tipos;
        tipos = ejb.consultaTipoProdutos();
        return tipos;
    }

}
