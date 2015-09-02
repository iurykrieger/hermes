package hermes.client.controll.produto;

import hermes.client.dao.ProdutoBean;
import hermes.ejb.entidades.Aplicacao;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iuryk
 */
public class AplicacaoController {
    
    private ProdutoBean bean;
    
    public AplicacaoController(){
        try {
            bean = new ProdutoBean();
        } catch (Exception ex) {
            Logger.getLogger(AplicacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Aplicacao buildAplicacao(String descricao){
        Aplicacao aplicacao = new Aplicacao();
        aplicacao.setDescricao(descricao);
        return aplicacao;
    }
    
    public Aplicacao registerAplicacao(Aplicacao aplicacao){
        try {
            aplicacao = bean.cadastraAplicacao(aplicacao);
            return aplicacao;
        } catch (Exception e) {
            return null;
        }
    }
}
