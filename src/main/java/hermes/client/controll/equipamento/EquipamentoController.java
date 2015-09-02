package hermes.client.controll.equipamento;

import hermes.client.dao.EquipamentoBean;
import hermes.ejb.entidades.Cliente;
import hermes.ejb.entidades.Equipamento;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class EquipamentoController {

    private EquipamentoBean bean;

    public EquipamentoController() {
        try {
            bean = new EquipamentoBean();
        } catch (Exception ex) {
            Logger.getLogger(EquipamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Equipamento cadastraEquipamento(Equipamento equipamento) {
        Equipamento e = new Equipamento();
        try {
            e = bean.cadastraEquipamento(equipamento);
        } catch (Exception ex) {
            Logger.getLogger(EquipamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public List<Equipamento> getEquipamentosByCliente(Cliente c) {
        List<Equipamento> equips = null;
        try {
            equips = bean.getEquipamentosByCliente(c);
        } catch (Exception ex) {
            Logger.getLogger(EquipamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equips;
    }

}
