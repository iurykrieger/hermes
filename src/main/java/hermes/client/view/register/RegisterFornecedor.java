package hermes.client.view.register;

import hermes.client.view.search.SearchFornecedor;
import com.hermes.components.HMFields.HMFormattedTextField;
import com.hermes.components.HMFrames.HMPanel;
import hermes.client.controll.fornecedor.FornecedorController;
import hermes.client.controll.utils.EstadosController;
import hermes.client.view.frame.Desktop;
import hermes.ejb.entidades.Estados;
import hermes.ejb.entidades.Fornecedor;
import hermes.ejb.entidades.FornecedorInfo;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author iury
 */
public class RegisterFornecedor extends HMPanel {

    private FornecedorInfo fornecedorInfo;
    private Fornecedor fornecedor;
    private SearchFornecedor frame;
    private int index = -1;

    public void initFields() {
        //TEXT FIELDS
        tf_celular.setMask(HMFormattedTextField.TELEFONE);
        tf_cep.setObrigatory(true);
        tf_cep.setMask(HMFormattedTextField.CEP);
        tf_cnpj.setObrigatory(true);
        tf_cnpj.setMask(HMFormattedTextField.CNPJ);
        tf_endereco.setObrigatory(true);
        tf_nome.setObrigatory(true);
        tf_nome_fantasia.setObrigatory(true);
        tf_telefone.setObrigatory(true);
        tf_telefone.setMask(HMFormattedTextField.TELEFONE);
        //COMBO BOXES
        cb_estado.setValues(new EstadosController().getEstados());
        verificaEstado();
    }

    public void verificaEstado() {
        cb_cidade.removeAllItems();
        cb_cidade.setValues(new EstadosController().getCidadesByEstado(((Estados) cb_estado.getSelectedItem())));
    }

    public boolean verificaCampos() {
        if (tf_nome.isValid() && tf_nome_fantasia.isValid() && tf_cnpj.isValid() && tf_telefone.isValid() && tf_email.isValid()
                && tf_celular.isValid() && tf_skype.isValid() && tf_numero.isValid() && tf_endereco.isValid() && tf_desc_end.isValid()
                && tf_cep.isValid()) {
            return true;
        } else {
            return false;
        }
    }

    public void cadastraFornecedor() {
        try {
            if (verificaCampos()) {
                FornecedorInfo fi = new FornecedorInfo();
                fornecedor.setNome(tf_nome.getText());
                fornecedor.setNomeFantasia(tf_nome_fantasia.getText());
                fornecedor.setCnpj(tf_cnpj.getText());
                fornecedorInfo.setTelefone(tf_telefone.getText());
                fornecedorInfo.setEmail(tf_email.getText());
                fornecedorInfo.setCelular(tf_celular.getText());
                fornecedorInfo.setSkype(tf_skype.getText());
                fornecedorInfo.setEstado(cb_estado.getSelectedItem().toString());
                fornecedorInfo.setCidade(cb_cidade.getSelectedItem().toString());
                fornecedorInfo.setEndereco(tf_endereco.getText());
                fornecedorInfo.setIdAtivo(true);
                fornecedorInfo.setDthAlteracao(new Date(System.currentTimeMillis()));

                fornecedor = new FornecedorController().cadastraFornecedor(fornecedor);
                new FornecedorController().setFornecedorInfosInactive(fornecedor);
                fornecedorInfo.setIdFornecedor(fornecedor);
                fornecedorInfo = new FornecedorController().cadastraFornecedorInfo(fornecedorInfo);

                JOptionPane.showMessageDialog(this, "Fornecedor salvo com sucesso!");
                if (index == -1) {
                    this.resetaPanel(this);
                    this.fornecedor = new Fornecedor();
                    this.fornecedorInfo = new FornecedorInfo();
                } else {
                    Desktop.desktop_pane.getSelectedFrame().dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Todos os campos obrigatórios devem ser preenchidos!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Não foi possível salvar o fornecedor!");
        }
    }

    public void buildFornecedor() {
        tf_nome.setText(this.fornecedor.getNome());
        tf_nome_fantasia.setText(this.fornecedor.getNomeFantasia());
        tf_cnpj.setText(this.fornecedor.getCnpj());
        tf_telefone.setText(this.fornecedorInfo.getTelefone());
        tf_celular.setText(this.fornecedorInfo.getCelular());
        tf_email.setText(this.fornecedorInfo.getEmail());
        tf_skype.setText(this.fornecedorInfo.getSkype());
        tf_endereco.setText(this.fornecedorInfo.getEndereco());
        cb_cidade.setSelectedItem(this.fornecedorInfo.getCidade());
        cb_estado.setSelectedItem(this.fornecedorInfo.getEstado());

        tf_nome.setValid(true);
        tf_nome_fantasia.setValid(true);
        tf_cnpj.setValid(true);
        tf_telefone.setValid(true);
        tf_email.setValid(true);
        tf_celular.setValid(true);
        tf_skype.setValid(true);
        tf_numero.setValid(true);
        tf_endereco.setValid(true);
        tf_desc_end.setValid(true);
        tf_cep.setValid(true);
    }

    public RegisterFornecedor() {
        this.fornecedorInfo = new FornecedorInfo();
        this.fornecedor = new Fornecedor();
        this.index = -1;
        initComponents();
        setSize(696, 338);
        initFields();
    }

    public RegisterFornecedor(FornecedorInfo fi, SearchFornecedor frame, int index) {
        this.fornecedorInfo = fi;
        this.index = index;
        this.fornecedor = fi.getIdFornecedor();
        this.frame = frame;
        initComponents();
        setSize(696, 338);
        initFields();
        buildFornecedor();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_cad_fornecedor = new javax.swing.JPanel();
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
        pn_dados_pessoais = new javax.swing.JPanel();
        lb_nome_fantasia = new javax.swing.JLabel();
        lb_nome = new javax.swing.JLabel();
        lb_cnpj = new javax.swing.JLabel();
        tf_nome = new com.hermes.components.HMFields.HMTextField();
        tf_cnpj = new com.hermes.components.HMFields.HMFormattedTextField();
        tf_nome_fantasia = new com.hermes.components.HMFields.HMTextField();
        pn_contato = new javax.swing.JPanel();
        lb_telefone = new javax.swing.JLabel();
        lb_celular = new javax.swing.JLabel();
        lb_email = new javax.swing.JLabel();
        tf_telefone = new com.hermes.components.HMFields.HMFormattedTextField();
        tf_celular = new com.hermes.components.HMFields.HMFormattedTextField();
        tf_email = new com.hermes.components.HMFields.HMTextField();
        lb_skype = new javax.swing.JLabel();
        tf_skype = new com.hermes.components.HMFields.HMTextField();
        jButton1 = new javax.swing.JButton();

        setOpaque(false);

        pn_cad_fornecedor.setOpaque(false);

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
                    .addComponent(tf_endereco, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(cb_estado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_localizacaoLayout.createSequentialGroup()
                        .addComponent(lb_cidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pn_localizacaoLayout.createSequentialGroup()
                        .addComponent(lb_descricao)
                        .addGap(5, 5, 5)))
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cb_cidade, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(tf_desc_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_numero)
                    .addComponent(lb_cep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(tf_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
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

        pn_dados_pessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Pessoais"));
        pn_dados_pessoais.setOpaque(false);

        lb_nome_fantasia.setText("Nome Fantasia:");

        lb_nome.setText("Nome:");

        lb_cnpj.setText("CNPJ:");

        javax.swing.GroupLayout pn_dados_pessoaisLayout = new javax.swing.GroupLayout(pn_dados_pessoais);
        pn_dados_pessoais.setLayout(pn_dados_pessoaisLayout);
        pn_dados_pessoaisLayout.setHorizontalGroup(
            pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dados_pessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_nome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(lb_nome_fantasia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_nome_fantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_cnpj)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pn_dados_pessoaisLayout.setVerticalGroup(
            pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dados_pessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_nome_fantasia)
                        .addComponent(tf_nome_fantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_cnpj)
                        .addComponent(tf_cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_nome)
                        .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        pn_contato.setBorder(javax.swing.BorderFactory.createTitledBorder("Contato"));
        pn_contato.setOpaque(false);

        lb_telefone.setText("Telefone:");

        lb_celular.setText("Celular:");

        lb_email.setText("E-mail:");

        lb_skype.setText("Skype:");

        javax.swing.GroupLayout pn_contatoLayout = new javax.swing.GroupLayout(pn_contato);
        pn_contato.setLayout(pn_contatoLayout);
        pn_contatoLayout.setHorizontalGroup(
            pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_contatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_skype)
                    .addComponent(lb_telefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_contatoLayout.createSequentialGroup()
                        .addComponent(tf_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(lb_celular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(pn_contatoLayout.createSequentialGroup()
                        .addComponent(tf_skype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pn_contatoLayout.setVerticalGroup(
            pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_contatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_telefone)
                    .addComponent(lb_celular)
                    .addComponent(tf_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_email)
                    .addComponent(tf_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_skype)
                    .addComponent(tf_skype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_cad_fornecedorLayout = new javax.swing.GroupLayout(pn_cad_fornecedor);
        pn_cad_fornecedor.setLayout(pn_cad_fornecedorLayout);
        pn_cad_fornecedorLayout.setHorizontalGroup(
            pn_cad_fornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cad_fornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cad_fornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pn_dados_pessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_localizacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_contato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_cad_fornecedorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pn_cad_fornecedorLayout.setVerticalGroup(
            pn_cad_fornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_cad_fornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_dados_pessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_localizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_cad_fornecedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_cad_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cb_estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_estadoActionPerformed
        verificaEstado();
    }//GEN-LAST:event_cb_estadoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cadastraFornecedor();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.hermes.components.HMSelections.HMComboBox cb_cidade;
    private com.hermes.components.HMSelections.HMComboBox cb_estado;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lb_celular;
    private javax.swing.JLabel lb_cep;
    private javax.swing.JLabel lb_cidade;
    private javax.swing.JLabel lb_cnpj;
    private javax.swing.JLabel lb_descricao;
    private javax.swing.JLabel lb_email;
    private javax.swing.JLabel lb_endereco;
    private javax.swing.JLabel lb_estado;
    private javax.swing.JLabel lb_nome;
    private javax.swing.JLabel lb_nome_fantasia;
    private javax.swing.JLabel lb_numero;
    private javax.swing.JLabel lb_skype;
    private javax.swing.JLabel lb_telefone;
    private javax.swing.JPanel pn_cad_fornecedor;
    private javax.swing.JPanel pn_contato;
    private javax.swing.JPanel pn_dados_pessoais;
    private javax.swing.JPanel pn_localizacao;
    private com.hermes.components.HMFields.HMFormattedTextField tf_celular;
    private com.hermes.components.HMFields.HMFormattedTextField tf_cep;
    private com.hermes.components.HMFields.HMFormattedTextField tf_cnpj;
    private com.hermes.components.HMFields.HMTextField tf_desc_end;
    private com.hermes.components.HMFields.HMTextField tf_email;
    private com.hermes.components.HMFields.HMTextField tf_endereco;
    private com.hermes.components.HMFields.HMTextField tf_nome;
    private com.hermes.components.HMFields.HMTextField tf_nome_fantasia;
    private com.hermes.components.HMFields.HMTextField tf_numero;
    private com.hermes.components.HMFields.HMTextField tf_skype;
    private com.hermes.components.HMFields.HMFormattedTextField tf_telefone;
    // End of variables declaration//GEN-END:variables
}
