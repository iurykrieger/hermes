package hermes.client.view.register;

import com.hermes.components.HMFields.HMFormattedTextField;
import com.hermes.components.HMFrames.HMPanel;
import com.hermes.components.HMTables.HMTableModel;
import com.hermes.components.utils.ImageFactory;
import hermes.client.controll.cliente.ClienteController;
import hermes.client.controll.utils.EstadosController;
import hermes.client.listener.produto.Dispatcher;
import hermes.client.listener.produto.PanelListener;
import hermes.client.listener.produto.PanelListenerInterface;
import hermes.client.main.Main;
import hermes.ejb.entidades.Cliente;
import hermes.ejb.entidades.ClienteInfo;
import hermes.ejb.entidades.Equipamento;
import hermes.ejb.entidades.Estados;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author iury
 */
public class RegisterCliente extends HMPanel {

    private ClienteController controller;
    
    public RegisterCliente() {
        controller = new ClienteController();
        initComponents();
        setSize(725, 499);
        initFields();
    }
    
    @Override
    public void salvar() {
        try {
            if (verificaCampos()) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String nome = tf_nome.getText();
                String sexo = cb_sexo.getSelectedItem().toString();
                String tipoPessoa = cb_tipo_pessoa.getSelectedItem().toString();
                String cnpj_cpf = tf_cpf_cnpj.getText();
                java.util.Date dataNasc = df.parse(tf_data_nasc.getText());
                Cliente cliente = controller.buildCliente(cnpj_cpf, dataNasc, nome, sexo, tipoPessoa);
                cliente = controller.registerCliente(cliente);
                if (cliente != null) {
                    controller.setClienteInfosInactive(cliente);
                    String celular = tf_celular.getText();
                    String cep = tf_cep.getText();
                    String cidade = cb_cidade.getSelectedItem().toString();
                    String email = tf_email.getText();
                    String endereco = tf_endereco.getText();
                    String enderecoDesc = tf_desc_end.getText();
                    String estado = cb_estado.getSelectedItem().toString();
                    String telefone = tf_telefone.getText();
                    ClienteInfo clienteInfo = controller.buildClienteInfo(celular, cep, cidade, email, endereco, enderecoDesc, estado, telefone);
                    clienteInfo = controller.registerClienteInfo(clienteInfo, cliente);
                    controller.registerEquipamentos(tbl_equipamento.getLista(), cliente);
                    JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Todos os campos obrigatórios devem ser preenchidos!");
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Não foi possível salvar cliente!");
        }
    }

    public void initFields() {
        //TEXT FIELDS
        tf_celular.setMask(HMFormattedTextField.TELEFONE);
        tf_cep.setObrigatory(true);
        tf_cep.setMask(HMFormattedTextField.CEP);
        tf_cpf_cnpj.setObrigatory(true);
        tf_cpf_cnpj.setMask(HMFormattedTextField.CPF);
        tf_data_nasc.setMask(HMFormattedTextField.DATA);
        tf_data_nasc.setObrigatory(true);
        tf_endereco.setObrigatory(true);
        tf_nome.setObrigatory(true);
        tf_telefone.setObrigatory(true);
        tf_telefone.setMask(HMFormattedTextField.TELEFONE);
        //COMBO BOXES
        String sexo[] = {"Masculino", "Feminino"};
        String pessoa[] = {"Física", "Jurídica"};
        cb_sexo.setValues(sexo);
        cb_tipo_pessoa.setValues(pessoa);
        cb_estado.setValues(new EstadosController().getEstados());
        verificaEstado();
        //TABLE
        String colunas[] = {"", "", "", "Nome", "Marca", "Descrição", "Tipo Produto", ""};
        String colunas_ocultas[] = {"ordemDeServicoList", "serialVersionUID", "idCliente", "idEquipamento"};
        tbl_equipamento.setModelo(new HMTableModel(null, Equipamento.class, colunas));
        tbl_equipamento.ocultarColunas(colunas_ocultas);
        btn_add_equip.setIcon(new ImageFactory().getIcon("add.png", 40, 40));
        btn_rem_equip.setIcon(new ImageFactory().getIcon("close.png", 40, 40));
        //LISTENERS
        PanelListenerInterface listener = new PanelListener(this);
        Dispatcher.getInstance().addListener(listener);
    }

    public void verificaTipoPessoa() {
        if (cb_tipo_pessoa.getSelectedItem().toString().equals("Física")) {
            tf_data_nasc.setObrigatory(true);
            pn_tipo_pessoa.setVisible(true);
            lb_cpf_cnpj.setText("CPF:");
            tf_cpf_cnpj.setMask(HMFormattedTextField.CPF);
        } else {
            tf_data_nasc.setObrigatory(false);
            pn_tipo_pessoa.setVisible(false);
            lb_cpf_cnpj.setText("CNPJ:");
            tf_cpf_cnpj.setMask(HMFormattedTextField.CNPJ);
        }
    }

    public void addEquipamento(Equipamento equipamento) {
        tbl_equipamento.addObject(equipamento);
        tbl_equipamento.updateUI();
    }

    public void alterEquipamento(Equipamento equipamento, int index) {
        tbl_equipamento.alterObject(equipamento, index);
        tbl_equipamento.updateUI();
    }

    public void remSelectedEquipamento() {
        tbl_equipamento.removeSelectedItem();
        tbl_equipamento.updateUI();
    }

    public void verificaEstado() {
        cb_cidade.removeAllItems();
        cb_cidade.setValues(new EstadosController().getCidadesByEstado(((Estados) cb_estado.getSelectedItem())));
    }

    public boolean verificaCampos() {
        return tf_celular.isValid() && tf_cep.isValid() && tf_cpf_cnpj.isValid() && tf_data_nasc.isValid() && tf_desc_end.isValid()
                && tf_email.isValid() && tf_endereco.isValid() && tf_nome.isValid() && tf_numero.isValid() && tf_telefone.isValid();
    }

    public void newEquipamento() {
        try {
            RegisterEquipamento ce = new RegisterEquipamento();
            Main.desktop.addInternalFrame(ce);
        } catch (Exception ex) {
            Logger.getLogger(RegisterCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alterEquipamento() {
        /*RegisterEquipamento ce;
        try {
            Equipamento e = (Equipamento) tbl_equipamento.getSelectedItem();
            ce = new RegisterEquipamento(e, tbl_equipamento.getSelectedRow());
            ce.setOwnerFrame(this);
            Main.desktop.addInternalFrame(ce);
        } catch (Exception ex) {
            Logger.getLogger(RegisterCliente.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_cadastro_cliente = new javax.swing.JPanel();
        pn_dados_pessoais = new javax.swing.JPanel();
        lb_nome = new javax.swing.JLabel();
        lb_tipo_pessoa = new javax.swing.JLabel();
        lb_cpf_cnpj = new javax.swing.JLabel();
        pn_tipo_pessoa = new javax.swing.JPanel();
        lb_data_nasc = new javax.swing.JLabel();
        lb_sexo = new javax.swing.JLabel();
        tf_data_nasc = new com.hermes.components.HMFields.HMFormattedTextField();
        cb_sexo = new com.hermes.components.HMSelections.HMComboBox();
        tf_nome = new com.hermes.components.HMFields.HMTextField();
        cb_tipo_pessoa = new com.hermes.components.HMSelections.HMComboBox();
        tf_cpf_cnpj = new com.hermes.components.HMFields.HMFormattedTextField();
        pn_localizacao = new javax.swing.JPanel();
        lb_estado = new javax.swing.JLabel();
        lb_cidade = new javax.swing.JLabel();
        lb_endereco = new javax.swing.JLabel();
        lb_numero = new javax.swing.JLabel();
        lb_cep = new javax.swing.JLabel();
        lb_descricao = new javax.swing.JLabel();
        cb_estado = new com.hermes.components.HMSelections.HMComboBox();
        cb_cidade = new com.hermes.components.HMSelections.HMComboBox();
        tf_numero = new com.hermes.components.HMFields.HMTextField();
        tf_endereco = new com.hermes.components.HMFields.HMTextField();
        tf_desc_end = new com.hermes.components.HMFields.HMTextField();
        tf_cep = new com.hermes.components.HMFields.HMFormattedTextField();
        pn_contato = new javax.swing.JPanel();
        lb_telefone = new javax.swing.JLabel();
        lb_celular = new javax.swing.JLabel();
        lb_email = new javax.swing.JLabel();
        tf_telefone = new com.hermes.components.HMFields.HMFormattedTextField();
        tf_celular = new com.hermes.components.HMFields.HMFormattedTextField();
        tf_email = new com.hermes.components.HMFields.HMTextField();
        pn_equipamento = new javax.swing.JPanel();
        btn_add_equip = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_equipamento = new com.hermes.components.HMTables.HMTable();
        btn_rem_equip = new javax.swing.JButton();

        setOpaque(false);

        pn_cadastro_cliente.setOpaque(false);

        pn_dados_pessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Pessoais"));
        pn_dados_pessoais.setOpaque(false);

        lb_nome.setText("Nome:");

        lb_tipo_pessoa.setText("Tipo Pessoa:");

        lb_cpf_cnpj.setText("CPF:");

        pn_tipo_pessoa.setOpaque(false);

        lb_data_nasc.setText("Data Nasc.:");

        lb_sexo.setText("Sexo:");

        javax.swing.GroupLayout pn_tipo_pessoaLayout = new javax.swing.GroupLayout(pn_tipo_pessoa);
        pn_tipo_pessoa.setLayout(pn_tipo_pessoaLayout);
        pn_tipo_pessoaLayout.setHorizontalGroup(
            pn_tipo_pessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tipo_pessoaLayout.createSequentialGroup()
                .addComponent(lb_data_nasc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_data_nasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(lb_sexo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pn_tipo_pessoaLayout.setVerticalGroup(
            pn_tipo_pessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tipo_pessoaLayout.createSequentialGroup()
                .addGroup(pn_tipo_pessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_data_nasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_sexo)
                    .addComponent(cb_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_data_nasc))
                .addGap(2, 2, 2))
        );

        cb_tipo_pessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_tipo_pessoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_dados_pessoaisLayout = new javax.swing.GroupLayout(pn_dados_pessoais);
        pn_dados_pessoais.setLayout(pn_dados_pessoaisLayout);
        pn_dados_pessoaisLayout.setHorizontalGroup(
            pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dados_pessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pn_tipo_pessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pn_dados_pessoaisLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lb_cpf_cnpj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_cpf_cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_dados_pessoaisLayout.createSequentialGroup()
                        .addComponent(lb_nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_tipo_pessoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_tipo_pessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_dados_pessoaisLayout.setVerticalGroup(
            pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dados_pessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cb_tipo_pessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_tipo_pessoa))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_nome)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_tipo_pessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_cpf_cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_cpf_cnpj))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn_localizacao.setBorder(javax.swing.BorderFactory.createTitledBorder("Locallização"));
        pn_localizacao.setOpaque(false);

        lb_estado.setText("Estado:");
        lb_estado.setMaximumSize(new java.awt.Dimension(29, 14));
        lb_estado.setMinimumSize(new java.awt.Dimension(29, 14));

        lb_cidade.setText("Cidade:");

        lb_endereco.setText("Endereço:");

        lb_numero.setText("Número:");

        lb_cep.setText("CEP:");

        lb_descricao.setText("Desc. End.:");

        cb_estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_estadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_localizacaoLayout = new javax.swing.GroupLayout(pn_localizacao);
        pn_localizacao.setLayout(pn_localizacaoLayout);
        pn_localizacaoLayout.setHorizontalGroup(
            pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_localizacaoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_endereco, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_estado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cb_estado, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(tf_endereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_localizacaoLayout.createSequentialGroup()
                        .addComponent(lb_cidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pn_localizacaoLayout.createSequentialGroup()
                        .addComponent(lb_descricao)
                        .addGap(5, 5, 5)))
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tf_desc_end, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(cb_cidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_numero)
                    .addComponent(lb_cep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_numero, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(tf_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_localizacaoLayout.setVerticalGroup(
            pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_localizacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_cidade)
                    .addComponent(lb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_numero)
                    .addComponent(tf_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_endereco)
                    .addComponent(tf_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_descricao)
                    .addComponent(tf_desc_end, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_cep)
                    .addComponent(tf_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pn_contato.setBorder(javax.swing.BorderFactory.createTitledBorder("Contato"));
        pn_contato.setOpaque(false);

        lb_telefone.setText("Telefone:");

        lb_celular.setText("Celular:");

        lb_email.setText("E-mail:");

        javax.swing.GroupLayout pn_contatoLayout = new javax.swing.GroupLayout(pn_contato);
        pn_contato.setLayout(pn_contatoLayout);
        pn_contatoLayout.setHorizontalGroup(
            pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_contatoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_contatoLayout.createSequentialGroup()
                        .addComponent(lb_celular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_contatoLayout.createSequentialGroup()
                        .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_email)
                            .addComponent(lb_telefone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_telefone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tf_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(201, 201, 201))
        );
        pn_contatoLayout.setVerticalGroup(
            pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_contatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_telefone)
                    .addComponent(tf_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_celular)
                    .addComponent(tf_celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_email)
                    .addComponent(tf_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pn_equipamento.setBorder(javax.swing.BorderFactory.createTitledBorder("Equipamentos"));
        pn_equipamento.setOpaque(false);

        btn_add_equip.setBackground(new java.awt.Color(224, 249, 252));
        btn_add_equip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_equipActionPerformed(evt);
            }
        });

        tbl_equipamento.setForeground(new java.awt.Color(0, 0, 0));
        tbl_equipamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_equipamento.setOpaque(false);
        tbl_equipamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_equipamentoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_equipamento);

        btn_rem_equip.setBackground(new java.awt.Color(224, 249, 252));
        btn_rem_equip.setForeground(new java.awt.Color(224, 249, 252));
        btn_rem_equip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rem_equipActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_equipamentoLayout = new javax.swing.GroupLayout(pn_equipamento);
        pn_equipamento.setLayout(pn_equipamentoLayout);
        pn_equipamentoLayout.setHorizontalGroup(
            pn_equipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_equipamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_equipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_add_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_rem_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_equipamentoLayout.setVerticalGroup(
            pn_equipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_equipamentoLayout.createSequentialGroup()
                .addGroup(pn_equipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_equipamentoLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btn_add_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btn_rem_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_equipamentoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pn_cadastro_clienteLayout = new javax.swing.GroupLayout(pn_cadastro_cliente);
        pn_cadastro_cliente.setLayout(pn_cadastro_clienteLayout);
        pn_cadastro_clienteLayout.setHorizontalGroup(
            pn_cadastro_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cadastro_clienteLayout.createSequentialGroup()
                .addGroup(pn_cadastro_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_cadastro_clienteLayout.createSequentialGroup()
                        .addComponent(pn_dados_pessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pn_contato, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pn_localizacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pn_equipamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_cadastro_clienteLayout.setVerticalGroup(
            pn_cadastro_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cadastro_clienteLayout.createSequentialGroup()
                .addGroup(pn_cadastro_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pn_dados_pessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_localizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_cadastro_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_cadastro_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cb_tipo_pessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_tipo_pessoaActionPerformed
        verificaTipoPessoa();
    }//GEN-LAST:event_cb_tipo_pessoaActionPerformed

    private void cb_estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_estadoActionPerformed
        verificaEstado();
    }//GEN-LAST:event_cb_estadoActionPerformed

    private void btn_add_equipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_equipActionPerformed
        newEquipamento();
    }//GEN-LAST:event_btn_add_equipActionPerformed

    private void tbl_equipamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_equipamentoMouseClicked
        if (evt.getClickCount() == 2) {
            alterEquipamento();
        }
    }//GEN-LAST:event_tbl_equipamentoMouseClicked

    private void btn_rem_equipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rem_equipActionPerformed
        remSelectedEquipamento();
    }//GEN-LAST:event_btn_rem_equipActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_equip;
    private javax.swing.JButton btn_rem_equip;
    private com.hermes.components.HMSelections.HMComboBox cb_cidade;
    private com.hermes.components.HMSelections.HMComboBox cb_estado;
    private com.hermes.components.HMSelections.HMComboBox cb_sexo;
    private com.hermes.components.HMSelections.HMComboBox cb_tipo_pessoa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_celular;
    private javax.swing.JLabel lb_cep;
    private javax.swing.JLabel lb_cidade;
    private javax.swing.JLabel lb_cpf_cnpj;
    private javax.swing.JLabel lb_data_nasc;
    private javax.swing.JLabel lb_descricao;
    private javax.swing.JLabel lb_email;
    private javax.swing.JLabel lb_endereco;
    private javax.swing.JLabel lb_estado;
    private javax.swing.JLabel lb_nome;
    private javax.swing.JLabel lb_numero;
    private javax.swing.JLabel lb_sexo;
    private javax.swing.JLabel lb_telefone;
    private javax.swing.JLabel lb_tipo_pessoa;
    private javax.swing.JPanel pn_cadastro_cliente;
    private javax.swing.JPanel pn_contato;
    private javax.swing.JPanel pn_dados_pessoais;
    private javax.swing.JPanel pn_equipamento;
    private javax.swing.JPanel pn_localizacao;
    private javax.swing.JPanel pn_tipo_pessoa;
    private com.hermes.components.HMTables.HMTable tbl_equipamento;
    private com.hermes.components.HMFields.HMFormattedTextField tf_celular;
    private com.hermes.components.HMFields.HMFormattedTextField tf_cep;
    private com.hermes.components.HMFields.HMFormattedTextField tf_cpf_cnpj;
    private com.hermes.components.HMFields.HMFormattedTextField tf_data_nasc;
    private com.hermes.components.HMFields.HMTextField tf_desc_end;
    private com.hermes.components.HMFields.HMTextField tf_email;
    private com.hermes.components.HMFields.HMTextField tf_endereco;
    private com.hermes.components.HMFields.HMTextField tf_nome;
    private com.hermes.components.HMFields.HMTextField tf_numero;
    private com.hermes.components.HMFields.HMFormattedTextField tf_telefone;
    // End of variables declaration//GEN-END:variables
}
