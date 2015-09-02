/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.estados.EstadosRemote;
import hermes.ejb.entidades.Cidades;
import hermes.ejb.entidades.Estados;
import hermes.ejb.utils.EjbConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class EstadosBean extends AbstractBean<EstadosRemote> {

    public EstadosBean() throws Exception {
        lookup(EjbConstants.CONTROLA_ESTADOS);
    }

    public List<Estados> consultaEstados() throws Exception {
        List<Estados> estados;
        estados = ejb.consultaEstados();
        return estados;
    }

    public List<Cidades> consultaCidades() throws Exception {
        List<Cidades> cidades;
        cidades = ejb.consultarCidades();
        return cidades;
    }

    public List<Cidades> consultaCidadesByEstado(Estados estado) {
        List<Cidades> cidades = new ArrayList();
        try {
            cidades = ejb.consultarCidadesByEstado(estado);
        } catch (Exception ex) {
            Logger.getLogger(EstadosBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return cidades;
        }
    }
}
