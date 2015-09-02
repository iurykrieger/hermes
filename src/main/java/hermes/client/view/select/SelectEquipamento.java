package hermes.client.view.select;

import hermes.client.view.register.RegisterOrdemServico;
import com.hermes.components.HMFrames.HMInternalFrame;
import com.hermes.components.HMTables.HMTableModel;
import hermes.client.controll.equipamento.EquipamentoController;
import hermes.ejb.entidades.ClienteInfo;
import hermes.ejb.entidades.Equipamento;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author iury
 */
public class SelectEquipamento extends HMInternalFrame {

    private String[] colunas = {"", "", "Nome", "Marca", "Descrição", "Tipo", "", ""};
    private String[] ocultas = {"serialVersionUID", "idEquipamento", "idCliente", "ordemDeServicoList"};
    private List<Equipamento> equipamentos;
    private RegisterOrdemServico os;
    private Equipamento selectedEquipamento;
    private ClienteInfo cliente;

    public void initFields() {
        //TEXT FIELDS
        tf_pesquisa.setStaticIcon("zoom.png", 20, 20);
        try {
            equipamentos = new EquipamentoController().getEquipamentosByCliente(cliente.getIdCliente());
        } catch (Exception ex) {
            Logger.getLogger(SelectEquipamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        tbl_pesquisa.setModelo(new HMTableModel(equipamentos, Equipamento.class, colunas));
        tbl_pesquisa.ocultarColunas(ocultas);
        tf_equipamento.setEditable(false);
        tf_equipamento.setEnabled(false);
    }

    public void onKeyReleased() {
        new Thread() {
            public void run() {
                //tbl_pesquisa.getModeloMC().setListas(new ClienteController().filtraClientes(tf_pesquisa.getText(), clientes));
            }
        }.start();
    }

    public void showEquipamento() {
        try {
            Equipamento e = (Equipamento) tbl_pesquisa.getSelectedItem();
            tf_equipamento.setText(e.getNome() + " - " + e.getIdTipoProduto().getNome());
            selectedEquipamento = e;
        } catch (Exception ex) {
            Logger.getLogger(SelectEquipamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectEquipamento() {
        if (!tf_equipamento.getText().isEmpty()) {
            try {
                os.addEquipamento(selectedEquipamento);
                this.dispose();
            } catch (Exception e) {
                Logger.getLogger(SelectEquipamento.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um equipamento!");
        }
    }

    public SelectEquipamento(ClienteInfo cliente, RegisterOrdemServico os) {
        initComponents();
        this.os = os;
        this.cliente = cliente;
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

        pn_pesquisa = new javax.swing.JPanel();
        pn_cliente = new javax.swing.JPanel();
        tf_equipamento = new javax.swing.JTextField();
        btn_ok = new javax.swing.JButton();
        pn_filtros = new javax.swing.JPanel();
        tf_pesquisa = new com.hermes.components.HMFields.HMTextField();
        pn_resultados = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pesquisa = new com.hermes.components.HMTables.HMTable();

        pn_pesquisa.setOpaque(false);

        pn_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Equipamento"));
        pn_cliente.setOpaque(false);

        tf_equipamento.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        btn_ok.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btn_ok.setText("OK");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_clienteLayout = new javax.swing.GroupLayout(pn_cliente);
        pn_cliente.setLayout(pn_clienteLayout);
        pn_clienteLayout.setHorizontalGroup(
            pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tf_equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_clienteLayout.setVerticalGroup(
            pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(tf_equipamento, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addComponent(btn_ok, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
        );

        pn_filtros.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));
        pn_filtros.setOpaque(false);

        tf_pesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_pesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pn_filtrosLayout = new javax.swing.GroupLayout(pn_filtros);
        pn_filtros.setLayout(pn_filtrosLayout);
        pn_filtrosLayout.setHorizontalGroup(
            pn_filtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_filtrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tf_pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_filtrosLayout.setVerticalGroup(
            pn_filtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_filtrosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tf_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pn_resultados.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));
        pn_resultados.setOpaque(false);

        tbl_pesquisa.setForeground(new java.awt.Color(0, 0, 0));
        tbl_pesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_pesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pesquisaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_pesquisa);

        javax.swing.GroupLayout pn_resultadosLayout = new javax.swing.GroupLayout(pn_resultados);
        pn_resultados.setLayout(pn_resultadosLayout);
        pn_resultadosLayout.setHorizontalGroup(
            pn_resultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_resultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pn_resultadosLayout.setVerticalGroup(
            pn_resultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_resultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_pesquisaLayout = new javax.swing.GroupLayout(pn_pesquisa);
        pn_pesquisa.setLayout(pn_pesquisaLayout);
        pn_pesquisaLayout.setHorizontalGroup(
            pn_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_pesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pn_filtros, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_resultados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_pesquisaLayout.setVerticalGroup(
            pn_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_pesquisaLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pn_filtros, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_resultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        selectEquipamento();
    }//GEN-LAST:event_btn_okActionPerformed

    private void tf_pesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_pesquisaKeyReleased
        onKeyReleased();
    }//GEN-LAST:event_tf_pesquisaKeyReleased

    private void tbl_pesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pesquisaMouseClicked
        showEquipamento();
        if (evt.getClickCount() == 2) {
            selectEquipamento();
        }
    }//GEN-LAST:event_tbl_pesquisaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ok;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pn_cliente;
    private javax.swing.JPanel pn_filtros;
    private javax.swing.JPanel pn_pesquisa;
    private javax.swing.JPanel pn_resultados;
    private com.hermes.components.HMTables.HMTable tbl_pesquisa;
    private javax.swing.JTextField tf_equipamento;
    private com.hermes.components.HMFields.HMTextField tf_pesquisa;
    // End of variables declaration//GEN-END:variables
}
