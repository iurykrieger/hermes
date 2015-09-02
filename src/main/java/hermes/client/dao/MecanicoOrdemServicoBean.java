/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.mecanico.MecanicoOrdemServicoRemote;
import hermes.ejb.entidades.MecanicoOrdemServico;
import hermes.ejb.entidades.OrdemDeServico;
import hermes.ejb.utils.EjbConstants;
import java.util.List;

/**
 *
 * @author iury
 */
public class MecanicoOrdemServicoBean extends AbstractBean<MecanicoOrdemServicoRemote> {

    public MecanicoOrdemServicoBean() throws Exception {
        lookup(EjbConstants.CONTROLA_MECANICO_ORDEM_SERVICO);
    }

    public MecanicoOrdemServico cadastraMecanicoOrdemServico(MecanicoOrdemServico mecanico) throws Exception {
        MecanicoOrdemServico m;
        m = ejb.cadastraMecanicoOrdemServico(mecanico);
        return m;
    }

    public List<MecanicoOrdemServico> consultaMecanicosOrdemServico() throws Exception {
        List<MecanicoOrdemServico> mecanicos;
        mecanicos = ejb.consultaMecanicosOrdemServico();
        return mecanicos;
    }

    public List<MecanicoOrdemServico> consultaMecanicosOrdemServicoByOrdemServico(OrdemDeServico os) throws Exception {
        List<MecanicoOrdemServico> mecanicos;
        mecanicos = ejb.consultaMecanicosOrdemServicoByOrdemServico(os);
        return mecanicos;
    }

}
