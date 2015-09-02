package hermes.client.controll.produto;

import com.hermes.components.HMFrames.HMPanel;
import com.hermes.components.HMSelections.HMComboBox;
import hermes.client.controll.tipoProduto.TipoProdutoController;
import hermes.client.controll.unidade.UnidadeController;
import hermes.client.dao.ProdutoBean;
import hermes.client.view.utils.FileChooserUtils;
import hermes.client.view.utils.ImageUtils;
import hermes.ejb.entidades.Aplicacao;
import hermes.ejb.entidades.Descricao;
import hermes.ejb.entidades.Estoque;
import hermes.ejb.entidades.Produto;
import hermes.ejb.entidades.TipoProduto;
import hermes.ejb.entidades.Unidade;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author iury
 */
public class ProdutoController {

    private ProdutoBean bean;

    public ProdutoController() {
        try {
            bean = new ProdutoBean();
        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<List<?>> filtraProdutos(String s, List<Estoque> estoques) {
        List<List<?>> listas = new ArrayList();
        List<Estoque> e = new ArrayList();
        List<Produto> p = new ArrayList();
        s = s.toLowerCase();
        for (Estoque tmp : estoques) {
            if (tmp.getIdProduto().getNome().toLowerCase().contains(s)
                    || tmp.getIdProduto().getLocalizacao().toLowerCase().contains(s)
                    || tmp.getIdProduto().getCodigo().toLowerCase().contains(s)
                    || tmp.getIdProduto().getIdTipoProduto().getNome().toLowerCase().contains(s)
                    || tmp.getIdProduto().getIdUnidade().getAbreviacao().toLowerCase().contains(s)
                    || tmp.getIdProduto().getLocalizacao().toLowerCase().contains(s)
                    || (String.valueOf(tmp.getCusto()).contains(s))
                    || (String.valueOf(tmp.getIdProduto().getMedidaExterna())).contains(s)
                    || (String.valueOf(tmp.getIdProduto().getMedidaInterna())).contains(s)
                    || (String.valueOf(tmp.getValorVenda())).contains(s)
                    || (String.valueOf(tmp.getQuantidade()).contains(s))) {
                e.add(tmp);
                p.add(tmp.getIdProduto());
            }
        }
        listas.add(p);
        listas.add(e);
        return listas;
    }

    public Estoque buildEstoque(BigDecimal custo, Produto idProduto, long quantidade, BigDecimal valorVenda) {
        try {
            Estoque e = new Estoque();
            e.setCusto(BigDecimal.ZERO);
            e.setDthAlteracao(new Date());
            e.setIdAtivo(true);
            e.setIdProduto(idProduto);
            e.setQuantidade(quantidade);
            e.setValorVenda(valorVenda);
            return e;
        } catch (Exception e) {
            return null;
        }
    }

    public Produto buildProduto(String codigo, String foto, TipoProduto tipoProduto, Unidade unidade,
            String localizacao, BigDecimal medidaExterna, BigDecimal medidaInterna, String nome) {
        try {
            Produto p = new Produto();
            p.setCodigo(codigo);
            p.setIdTipoProduto(tipoProduto);
            p.setIdUnidade(unidade);
            p.setLocalizacao(localizacao);
            p.setMedidaExterna(medidaExterna);
            p.setMedidaInterna(medidaInterna);
            p.setNome(nome);
            uploadImage(foto, p);
            return p;
        } catch (Exception e) {
            return null;
        }
    }

    public Produto registerProduto(Produto produto) {
        try {
            produto = bean.cadastraProduto(produto);
            return produto;
        } catch (Exception e) {
            return null;
        }
    }

    public Estoque registerEstoque(Produto produto, Estoque estoque) {
        try {
            estoque.setIdProduto(produto);
            estoque = bean.cadastraEstoque(estoque);
            return estoque;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Descricao> registerDescricoes(Produto produto, List<Descricao> descricoes) {
        try {
            for (Descricao d : descricoes) {
                d.setIdProduto(produto);
                d = new DescricaoController().registerDescricao(d);
            }
            return descricoes;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Aplicacao> registerAplicacoes(Produto produto, List<Aplicacao> aplicacoes) {
        try {
            for (Aplicacao a : aplicacoes) {
                a.setIdProduto(produto);
                a = new AplicacaoController().registerAplicacao(a);
            }
            return aplicacoes;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean setEstoquesInative(Produto p) {
        try {
            bean.setEstoquesInactive(p);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getImage(JFileChooser fileChooser, HMPanel panel) {
        File f = FileChooserUtils.getFile(fileChooser, panel);
        if (f != null) {
            if (ImageUtils.isValidExtension(f)) {
                return f.getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(panel, "Arquivo de formato inválido! Utilize apenas JPG, JPEG ou PNG", "Alerta", JOptionPane.WARNING_MESSAGE);
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Arquivo não encontrado", "Alerta", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public void saveImage(String imagePath, JFileChooser fileChooser, HMPanel panel) {
        if (imagePath != null && !imagePath.isEmpty()) {
            String path = FileChooserUtils.getPath(fileChooser, panel);
            ImageUtils.moveImageToPath(imagePath, path);
        } else {
            JOptionPane.showMessageDialog(panel, "Você precisa ter uma foto adicionada", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }

    public Produto uploadImage(String imagePath, Produto produto) {
        try {
            File foto = new File(imagePath);
            String newFileName = "HPHOTO" + System.currentTimeMillis();
            File newFoto = new File(getClass().getResource("/images/").getPath(), newFileName);
            foto.renameTo(newFoto);
            produto.setFoto(newFoto.getAbsolutePath());
            return produto;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível mover a foto", "Alerta", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public void loadTipoProduto(HMComboBox comboBox) {
        try {
            List<TipoProduto> tipos = new ArrayList();
            tipos = new TipoProdutoController().getTipos();
            if (!tipos.isEmpty()) {
                comboBox.setValues(tipos);
            }
        } catch (Exception ex) {

        }
    }

    public void loadUnidade(HMComboBox comboBox) {
        try {
            List<Unidade> unidades = new ArrayList();
            unidades = new UnidadeController().getUnidades();
            if (!unidades.isEmpty()) {
                comboBox.setValues(unidades);
            }
        } catch (Exception ex) {

        }
    }
}
