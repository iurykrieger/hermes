package hermes.client.view.select;

import hermes.client.view.register.RegisterOrdemServico;
import com.hermes.components.HMFields.HMFormattedTextField;
import com.hermes.components.HMFrames.HMInternalFrame;
import com.hermes.components.HMTables.HMMultipleClassesTableModel;
import hermes.client.controll.mecanico.MecanicoController;
import hermes.ejb.entidades.Mecanico;
import hermes.ejb.entidades.MecanicoInfo;
import hermes.ejb.entidades.MecanicoOrdemServico;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author iury
 */
public class SelectMecanico extends HMInternalFrame {

    private String[] colunas = {"serialVersionUID", "idMecanico", "Nome", "CPF", "Sexo", "Data Nascimento", "mecanicoInfoList", "mecanicoOrdemFabricacaoList", "mecanicoOrdemServicoList",
        "serialVersionUID", "idMecanicoInfo", "Valor Hora", "idAtivo", "Endereço", "Telefone", "Celular", "Carga Horária", "enderecoDescricao", "dthAlteracao", "Email", "foto", "Cidade", "Estado",
        "CEP", "idMecanico"};
    private String[] ocultas = {"serialVersionUID", "idMecanico", "dataNascimento", "mecanicoInfoList", "mecanicoOrdemFabricacaoList", "mecanicoOrdemServicoList", "idMecanicoInfo",
        "idAtivo", "endereco", "celular", "enderecoDescricao", "dthAlteracao", "email", "foto", "cidade", "estado", "cep"};
    private List<Class<?>> classes;
    private List<MecanicoInfo> mecanicos;
    private RegisterOrdemServico os;
    private MecanicoInfo selectedMecanico;

    public void initFields() {
        //TEXT FIELDS
        tf_pesquisa.setStaticIcon("zoom.png", 20, 20);
        tf_hora_inicio.setMask(HMFormattedTextField.HORA);
        tf_hora_fim.setMask(HMFormattedTextField.HORA);
        tf_hora_inicio.setObrigatory(true);
        tf_hora_fim.setObrigatory(true);
        //TABLE
        List<List<?>> listas = new ArrayList();
        classes = new ArrayList();
        try {
            mecanicos = new MecanicoController().consultaMecanicoInfosByIdAtivo();
            List<Mecanico> m = new ArrayList();
            for (MecanicoInfo mecanico : mecanicos) {
                m.add(mecanico.getIdMecanico());
            }
            listas.add(m);
            listas.add(mecanicos);
            classes.add(Mecanico.class);
            classes.add(MecanicoInfo.class);
            tbl_pesquisa.setModelo(new HMMultipleClassesTableModel(listas, classes, colunas));
            tbl_pesquisa.ocultarColunas(ocultas);
            tf_mecanico.setEditable(false);
            tf_mecanico.setEnabled(false);
        } catch (Exception ex) {
            Logger.getLogger(SelectMecanico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onKeyReleased() {
        new Thread() {
            public void run() {
                tbl_pesquisa.getModeloMC().setListas(new MecanicoController().filtraMecanicos(tf_pesquisa.getText(), mecanicos));
            }
        }.start();
    }

    public void showProduto() {
        MecanicoInfo m = null;
        List<?> l = tbl_pesquisa.getSelectedItemMC();
        for (Object ob : l) {
            if (ob instanceof MecanicoInfo) {
                m = (MecanicoInfo) ob;
            }
        }
        try {
            tf_mecanico.setText(m.getIdMecanico().getNome() + " - " + m.getIdMecanico().getCpf());
            selectedMecanico = m;
        } catch (Exception ex) {
            Logger.getLogger(SelectMecanico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectMecanico() {
        if (!tf_mecanico.getText().isEmpty()) {
            if (tf_hora_fim.isValid() && tf_hora_fim.isValid()) {
                try {
                    MecanicoOrdemServico mos = new MecanicoOrdemServico();
                    mos.setIdMecanico(selectedMecanico.getIdMecanico());
                    String vet[] = tf_hora_inicio.getText().split(":");
                    String vet2[] = tf_hora_fim.getText().split(":");
                    double minutos1 = (Double.valueOf(vet[0]) * 60) + Double.valueOf(vet[1]);
                    double minutos2 = (Double.valueOf(vet2[0]) * 60) + Double.valueOf(vet2[1]);
                    double qtdadeTempo = minutos2 - minutos1;
                    if (qtdadeTempo < 1) {
                        JOptionPane.showMessageDialog(this, "A hora final deve ser maior que a inicial!");
                    } else {
                        mos.setNumeroHoras(BigDecimal.valueOf(qtdadeTempo / 60));
                        os.addMecanico(mos);
                        this.dispose();
                    }
                } catch (Exception e) {
                    Logger.getLogger(SelectMecanico.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Horários inválidos!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um mecânico!");
        }
    }

    public SelectMecanico(RegisterOrdemServico os) {
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

        pn_pesquisa = new javax.swing.JPanel();
        pn_cliente = new javax.swing.JPanel();
        tf_mecanico = new javax.swing.JTextField();
        btn_ok = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tf_hora_inicio = new com.hermes.components.HMFields.HMFormattedTextField();
        tf_hora_fim = new com.hermes.components.HMFields.HMFormattedTextField();
        pn_filtros = new javax.swing.JPanel();
        tf_pesquisa = new com.hermes.components.HMFields.HMTextField();
        pn_resultados = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pesquisa = new com.hermes.components.HMTables.HMTable();

        pn_pesquisa.setOpaque(false);

        pn_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Mecânico"));
        pn_cliente.setOpaque(false);

        tf_mecanico.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        btn_ok.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btn_ok.setText("OK");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        jLabel1.setText("Início:");

        jLabel2.setText("Fim:");

        tf_hora_fim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_hora_fimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_clienteLayout = new javax.swing.GroupLayout(pn_cliente);
        pn_cliente.setLayout(pn_clienteLayout);
        pn_clienteLayout.setHorizontalGroup(
            pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tf_mecanico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_hora_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_hora_fim, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_ok, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pn_clienteLayout.setVerticalGroup(
            pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_clienteLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(tf_mecanico, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_clienteLayout.createSequentialGroup()
                        .addGroup(pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_clienteLayout.createSequentialGroup()
                                .addGroup(pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(tf_hora_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(tf_hora_fim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btn_ok, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
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
                .addGroup(pn_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_resultados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_filtros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
            .addComponent(pn_pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        selectMecanico();
    }//GEN-LAST:event_btn_okActionPerformed

    private void tf_hora_fimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_hora_fimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_hora_fimActionPerformed

    private void tf_pesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_pesquisaKeyReleased
        onKeyReleased();
    }//GEN-LAST:event_tf_pesquisaKeyReleased

    private void tbl_pesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pesquisaMouseClicked
        showProduto();
        if (evt.getClickCount() == 2) {
            selectMecanico();
        }
    }//GEN-LAST:event_tbl_pesquisaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pn_cliente;
    private javax.swing.JPanel pn_filtros;
    private javax.swing.JPanel pn_pesquisa;
    private javax.swing.JPanel pn_resultados;
    private com.hermes.components.HMTables.HMTable tbl_pesquisa;
    private com.hermes.components.HMFields.HMFormattedTextField tf_hora_fim;
    private com.hermes.components.HMFields.HMFormattedTextField tf_hora_inicio;
    private javax.swing.JTextField tf_mecanico;
    private com.hermes.components.HMFields.HMTextField tf_pesquisa;
    // End of variables declaration//GEN-END:variables
}
