package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.equipamento.EquipamentoRemote;
import hermes.ejb.entidades.Cliente;
import hermes.ejb.entidades.Equipamento;
import hermes.ejb.utils.EjbConstants;
import java.util.List;

public class EquipamentoBean extends AbstractBean<EquipamentoRemote> {

    public EquipamentoBean() throws Exception {
        lookup(EjbConstants.CONTROLA_EQUIPAMENTO);
    }

    public List<Cliente> consultaClientes() throws Exception {
        List<Cliente> clientes;
        clientes = ejb.consultaClientes();
        return clientes;
    }

    public Equipamento cadastraEquipamento(Equipamento equipamento) throws Exception {
        Equipamento e;
        e = ejb.cadastraEquipamento(equipamento);
        return e;
    }

    public List<Equipamento> getEquipamentosByCliente(Cliente c) throws Exception {
        List<Equipamento> equips;
        equips = ejb.getEquipamentosByCliente(c);
        return equips;
    }
}
