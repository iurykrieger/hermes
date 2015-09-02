package hermes.client.controll.cliente;

import hermes.client.controll.equipamento.EquipamentoController;
import hermes.client.dao.ClienteBean;
import hermes.client.view.register.RegisterCliente;
import hermes.ejb.entidades.Cliente;
import hermes.ejb.entidades.ClienteInfo;
import hermes.ejb.entidades.Equipamento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JCheckBox;

/**
 *
 * @author iury
 */
public class ClienteController {

    private ClienteBean bean;

    public ClienteController() {
        try {
            bean = new ClienteBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cliente buildCliente(String cnpj_cpf, Date dataNasc, String nome, String sexo, String tipoPessoa) {
        Cliente cliente = new Cliente();
        cliente.setDataNascimento(dataNasc);
        cliente.setNome(nome);
        cliente.setSexo(sexo);
        cliente.setTipoPessoa(tipoPessoa);
        if (tipoPessoa.equals("Física")) {
            cliente.setCpf(cnpj_cpf);
            cliente.setCnpj(null);
        } else {
            cliente.setCnpj(cnpj_cpf);
            cliente.setCpf(null);
        }
        return cliente;
    }

    public ClienteInfo buildClienteInfo(String celular, String cep, String cidade, String email, String endereco,
            String enderecoDesc, String estado, String telefone) {
        ClienteInfo clienteInfo = new ClienteInfo();
        clienteInfo.setCelular(celular);
        clienteInfo.setCep(cep);
        clienteInfo.setCidade(cidade);
        clienteInfo.setCredibilidade(10);
        clienteInfo.setDthAlteracao(new Date(System.currentTimeMillis()));
        clienteInfo.setEmail(email);
        clienteInfo.setEndereco(endereco);
        clienteInfo.setEnderecoDescricao(enderecoDesc);
        clienteInfo.setEstado(estado);
        clienteInfo.setIdAtivo(true);
        clienteInfo.setTelefone(telefone);
        return clienteInfo;
    }

    public Cliente registerCliente(Cliente cliente) {
        try {
            return bean.cadastraCliente(cliente);
        } catch (Exception e) {
            return null;
        }
    }

    public ClienteInfo registerClienteInfo(ClienteInfo clienteInfo, Cliente cliente) {
        try {
            clienteInfo.setIdCliente(cliente);
            return bean.cadastraClienteInfo(clienteInfo);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Equipamento> registerEquipamentos(List<Equipamento> equipamentos, Cliente c){
        try {
            for (Equipamento e : equipamentos) {
                e.setIdCliente(c);
                e = new EquipamentoController().cadastraEquipamento(e);
            }
            return equipamentos;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean setClienteInfosInactive(Cliente c) {
        try {
            bean.setClienteInfosInactive(c);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<List<?>> filtraClientes(String s, List<ClienteInfo> clientes) {
        List<List<?>> listas = new ArrayList();
        List<ClienteInfo> ci = new ArrayList();
        List<Cliente> c = new ArrayList();
        for (ClienteInfo tmp : clientes) {
            String cpf = tmp.getIdCliente().getCpf();
            String cnpj = tmp.getIdCliente().getCnpj();
            if (cpf == null) {
                tmp.getIdCliente().setCpf("");
            }
            if (cnpj == null) {
                tmp.getIdCliente().setCnpj("");
            }
            if (tmp.getIdCliente().getNome().contains(s) || tmp.getIdCliente().getSexo().contains(s) || tmp.getIdCliente().getCpf().contains(s)
                    || tmp.getIdCliente().getTipoPessoa().contains(s) || tmp.getEndereco().contains(s) || tmp.getTelefone().contains(s)
                    || tmp.getCelular().contains(s) || tmp.getEnderecoDescricao().contains(s) || tmp.getEmail().contains(s)
                    || tmp.getCidade().contains(s) || tmp.getEstado().contains(s) || tmp.getIdCliente().getCnpj().contains(s)) {
                ci.add(tmp);
                c.add(tmp.getIdCliente());
            }
        }
        listas.add(c);
        listas.add(ci);
        return listas;
    }

    public List<List<?>> filtraClientesByCheckbox(String s, List<ClienteInfo> clientes, List<JCheckBox> boxes) {

        return null;
    }

}
