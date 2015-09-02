package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.cliente.ClienteRemote;
import hermes.ejb.entidades.Cliente;
import hermes.ejb.entidades.ClienteInfo;
import hermes.ejb.utils.EjbConstants;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteBean extends AbstractBean<ClienteRemote> {

    public ClienteBean() {
        try {
            lookup(EjbConstants.CONTROLA_CLIENTE);
        } catch (Exception ex) {
            Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cliente cadastraCliente(Cliente cliente) {
        try {
            Cliente c;
            c = ejb.cadastraCliente(cliente);
            return c;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }

    public List<Cliente> consultaCliente() throws Exception {
        List<Cliente> clientes;
        clientes = ejb.consultaClientes();
        return clientes;
    }

    public ClienteInfo cadastraClienteInfo(ClienteInfo clienteinfo) throws Exception {
        ClienteInfo c;
        c = ejb.cadastraClienteInfo(clienteinfo);
        return c;
    }

    public List<ClienteInfo> consultaClienteInfos() throws Exception {
        List<ClienteInfo> clientes;
        clientes = ejb.consultaClienteInfos();
        return clientes;
    }

    public List<ClienteInfo> consultaClienteInfosByIdAtivo() throws Exception {
        List<ClienteInfo> clientes;
        clientes = ejb.consultaClienteInfoByIdAtivo();
        return clientes;
    }

    public void setClienteInfosInactive(Cliente c) throws Exception {
        ejb.setClienteInfosInactive(c);
    }

    public ClienteInfo getClienteInfoByCliente(Cliente c) throws Exception {
        ClienteInfo ci = new ClienteInfo();
        ci = ejb.getClienteInfoByCliente(c);
        return ci;
    }
}
