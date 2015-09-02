/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.dao;

import hermes.client.controll.produto.ProdutoController;
import hermes.client.core.AbstractBean;
import hermes.ejb.controller.produto.ProdutoRemote;
import hermes.ejb.entidades.Aplicacao;
import hermes.ejb.entidades.Descricao;
import hermes.ejb.entidades.Estoque;
import hermes.ejb.entidades.Produto;
import hermes.ejb.utils.EjbConstants;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class ProdutoBean extends AbstractBean<ProdutoRemote> {

    public ProdutoBean() throws Exception {
        try {
            lookup(EjbConstants.CONTROLA_PRODUTO);
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public Produto cadastraProduto(Produto produto) {
        try {
            Produto p;
            p = ejb.cadastraProduto(produto);
            return p;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public Descricao cadastraDescricao(Descricao descricao) {
        try {
            Descricao d;
            d = ejb.cadastraDescricao(descricao);
            return d;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public Aplicacao cadastraAplicacao(Aplicacao aplicacacao) {
        try {
            Aplicacao a;
            a = ejb.cadastraAplicacao(aplicacacao);
            return a;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<Produto> consultaProdutos() {
        try {
            List<Produto> produtos;
            produtos = ejb.consultaProdutos();
            return produtos;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public Estoque cadastraEstoque(Estoque produtoinfo) {
        try {
            Estoque e;
            e = ejb.cadastraEstoque(produtoinfo);
            return e;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<Estoque> consultaEstoques() {
        try {
            List<Estoque> produtos;
            produtos = ejb.consultaEstoques();
            return produtos;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<Estoque> consultaEstoquesByIdAtivo() {
        try {
            List<Estoque> produtos;
            produtos = ejb.consultaEstoquesByIdAtivo();
            return produtos;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<Estoque> consultaValidIdAtivoEstoques() {
        try {
            List<Estoque> produtos;
            produtos = ejb.consultaValidIdAtivoEstoques();
            return produtos;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public void setEstoquesInactive(Produto p) {
        try {
            ejb.setEstoquesInactive(p);
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<Descricao> getDescricoesByProduto(Produto p) {
        try {
            List<Descricao> descricoes;
            descricoes = ejb.getDescricoesByProduto(p);
            return descricoes;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<Aplicacao> getAplicacoesByProduto(Produto p) {
        try {
            List<Aplicacao> descricoes;
            descricoes = ejb.getAplicacoesByProduto(p);
            return descricoes;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public Estoque consultaEstoqueByProduto(Produto p) {
        try {
            Estoque e;
            e = ejb.consultaEstoqueByProduto(p);
            return e;
        } catch (Exception e) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
}
