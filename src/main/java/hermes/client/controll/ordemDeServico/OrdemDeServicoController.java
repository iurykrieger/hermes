package hermes.client.controll.ordemDeServico;

import hermes.client.controll.mecanico.MecanicoController;
import hermes.client.dao.OrdemDeServicoBean;
import hermes.client.controll.produto.ProdutoController;
import hermes.client.dao.ProdutoBean;
import hermes.ejb.entidades.Estoque;
import hermes.ejb.entidades.ItemExternoOrdemServico;
import hermes.ejb.entidades.ItemOrdemServico;
import hermes.ejb.entidades.MecanicoInfo;
import hermes.ejb.entidades.MecanicoOrdemServico;
import hermes.ejb.entidades.OrdemDeServico;
import hermes.ejb.entidades.ParcelaOdemDeServico;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class OrdemDeServicoController {

    private OrdemDeServicoBean bean;

    public OrdemDeServicoController() {
        try {
            bean = new OrdemDeServicoBean();
        } catch (Exception ex) {
            Logger.getLogger(OrdemDeServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OrdemDeServico cadastraOrdemDeServico(OrdemDeServico os) {
        OrdemDeServico ods = new OrdemDeServico();
        try {
            ods = bean.cadastraOrdemDeServico(os);
        } catch (Exception ex) {
            Logger.getLogger(OrdemDeServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ods;
    }

    public List<OrdemDeServico> consultaOrdemDeServicos() {
        List<OrdemDeServico> os = new ArrayList();
        try {
            os = bean.consultaOrdemDeServicos();
        } catch (Exception ex) {
            Logger.getLogger(OrdemDeServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return os;
    }

    public List<OrdemDeServico> consultaOrdemDeServicoByNotPayed() {
        List<OrdemDeServico> os = new ArrayList();
        try {
            os = bean.consultaOrdemDeServicoByNotPayed();
        } catch (Exception ex) {
            Logger.getLogger(OrdemDeServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return os;
    }

    public double calcularTotalProduos(List<ItemOrdemServico> ios, List<ItemExternoOrdemServico> ieos) {
        double total = 0;
        for (ItemExternoOrdemServico i : ieos) {
            total += i.getValorVenda().doubleValue() * i.getQuantidade().doubleValue();
        }
        for (ItemOrdemServico i : ios) {
            Estoque e;
            try {
                e = new ProdutoBean().consultaEstoqueByProduto(i.getIdProduto());
                total += e.getValorVenda().doubleValue() * i.getQuantidade().doubleValue();
            } catch (Exception ex) {
                Logger.getLogger(OrdemDeServicoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return total;
    }

    public double calcularTotalServicos(List<MecanicoOrdemServico> mos) {
        double total = 0;
        for (MecanicoOrdemServico m : mos) {
            MecanicoInfo mi = new MecanicoController().consultaMecanicoInfoByMecanico(m.getIdMecanico());
            total += mi.getValorHora().doubleValue() * m.getNumeroHoras().doubleValue();
        }
        return total;
    }

    public String calcularParcelas(List<ParcelaOdemDeServico> parcelas) {
        ParcelaOdemDeServico parcela = parcelas.get(0);
        String s = parcela.getNumeroParcelas() + "x de R$ " + parcela.getValorParcela();
        return s;
    }

}
