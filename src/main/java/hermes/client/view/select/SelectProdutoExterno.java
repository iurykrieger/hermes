package hermes.client.view.select;

import hermes.client.view.register.RegisterOrdemServico;
import com.hermes.components.HMFrames.HMInternalFrame;
import hermes.client.controll.frame.DesktopController;
import hermes.client.main.Main;
import hermes.ejb.entidades.FornecedorInfo;
import hermes.ejb.entidades.ItemExternoOrdemServico;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author iury
 */
public class SelectProdutoExterno extends HMInternalFrame {

    private RegisterOrdemServico os;
    private FornecedorInfo fornecedor;

    public void initFields() {
        tf_fornecedor.setObrigatory(true);
        tf_fornecedor.setEnabled(false);
        tf_fornecedor.setEditable(false);
        tf_nome.setObrigatory(true);
    }

    public void addFornecedor(FornecedorInfo fi) {
        this.fornecedor = fi;
        tf_fornecedor.setText(fornecedor.getIdFornecedor().getNome());
        tf_fornecedor.setValid(true);
    }

    public boolean verificaCampos() {
        if (tf_fornecedor.isValid() && tf_nome.isValid()) {
            return true;
        } else {
            return false;
        }
    }

    public void selectProdutoExterno() {
        if (verificaCampos()) {
            try {
                ItemExternoOrdemServico ieos = new ItemExternoOrdemServico();
                ieos.setNome(tf_nome.getText());
                ieos.setDescricao(tp_descricao.getText());
                ieos.setIdFornecedor(this.fornecedor.getIdFornecedor());
                ieos.setCusto(tf_custo.getValue());
                ieos.setValorVenda(tf_valor_venda.getValue());
                Double value = Double.valueOf(spn_qtdade.getValue().toString());
                ieos.setQuantidade(BigDecimal.valueOf(value));
                os.addProdutoExterno(ieos);
                this.dispose();
            } catch (Exception e) {
                Logger.getLogger(SelectProdutoExterno.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Todos os campos obrigatórios devem ser preenchidos!");
        }
    }

    public void searchFornecedor() {
        try {
            SelectFornecedor sf = new SelectFornecedor(this);
            Main.desktop.addInternalFrame(sf);
        } catch (Exception ex) {
            Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SelectProdutoExterno(RegisterOrdemServico os) {
        initComponents();
        this.os = os;
        new Thread() {
            public void run() {
                initFields();
            }
        }.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tf_nome = new com.hermes.components.HMFields.HMTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tp_descricao = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        tf_custo = new com.hermes.components.HMFields.HMMonetaryField();
        jLabel4 = new javax.swing.JLabel();
        tf_valor_venda = new com.hermes.components.HMFields.HMMonetaryField();
        jLabel5 = new javax.swing.JLabel();
        tf_fornecedor = new com.hermes.components.HMFields.HMTextField();
        btn_add_fornecedor = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        spn_qtdade = new javax.swing.JSpinner();
        btn_add = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Item Externo"));
        jPanel1.setOpaque(false);

        jLabel1.setText("Nome:");

        jLabel2.setText("Descrição:");

        jScrollPane1.setViewportView(tp_descricao);

        jLabel3.setText("Custo:");

        jLabel4.setText("Valor Venda:");

        jLabel5.setText("Fornecedor:");

        btn_add_fornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_fornecedorActionPerformed(evt);
            }
        });

        jLabel6.setText("Quantidade:");

        btn_add.setText("Adicionar");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");
        btn_cancelar.setMaximumSize(new java.awt.Dimension(75, 26));
        btn_cancelar.setMinimumSize(new java.awt.Dimension(75, 26));
        btn_cancelar.setPreferredSize(new java.awt.Dimension(75, 26));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(tf_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_add_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tf_nome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1)
                        .addComponent(tf_custo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tf_valor_venda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(spn_qtdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_add_fornecedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(tf_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tf_custo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tf_valor_venda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spn_qtdade, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_add_fornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_fornecedorActionPerformed
        searchFornecedor();
    }//GEN-LAST:event_btn_add_fornecedorActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        selectProdutoExterno();
    }//GEN-LAST:event_btn_addActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_add_fornecedor;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spn_qtdade;
    private com.hermes.components.HMFields.HMMonetaryField tf_custo;
    private com.hermes.components.HMFields.HMTextField tf_fornecedor;
    private com.hermes.components.HMFields.HMTextField tf_nome;
    private com.hermes.components.HMFields.HMMonetaryField tf_valor_venda;
    private javax.swing.JTextPane tp_descricao;
    // End of variables declaration//GEN-END:variables
}
