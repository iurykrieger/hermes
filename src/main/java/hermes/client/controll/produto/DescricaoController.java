package hermes.client.controll.produto;

import hermes.client.dao.ProdutoBean;
import hermes.ejb.entidades.Descricao;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iuryk
 */
public class DescricaoController {
    
    private ProdutoBean bean;
    
    public DescricaoController(){
        try {
            bean = new ProdutoBean();
        } catch (Exception ex) {
            Logger.getLogger(DescricaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Descricao buildDescricao(String desc){
        Descricao descricao = new Descricao();
        descricao.setDescricao(desc);
        return descricao;
    }
    
    public Descricao registerDescricao(Descricao descricao){
        try {
            descricao = bean.cadastraDescricao(descricao);
            return descricao;
        } catch (Exception e) {
            return null;
        }
    }
    
}
