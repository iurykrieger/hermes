package hermes.client.view.frame;

import com.hermes.components.utils.ImageFactory;
import hermes.client.controll.frame.LoadingController;
import static java.lang.Thread.sleep;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author iury
 */
public class Loading extends javax.swing.JFrame {

    public Loading() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(new ImageFactory().getIcon("hermes_icon.png", 600, 600).getImage());
        setAlwaysOnTop(true);
    }

    public boolean loadHermes() {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean bool = new AtomicBoolean(false);
        new Thread() {
            public void run() {
                System.out.println("Thread iniciada");
                LoadingController lc = new LoadingController();
                if (lc.load()) {
                    bool.set(true);
                } else {
                    bool.set(false);
                }
                latch.countDown();
            }
        }.start();
        new Thread() {
            public void run() {
                loadBar();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Loading.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bool.get();
    }

    public void loadBar() {
        for (int i = 0; i <= 100; i++) {
            try {
                sleep(125);
                pb_loading.setValue(i);
                if (pb_loading.getValue() <= 25) {
                    lb_loading.setText("Estabelecendo conexão...");
                } else if (pb_loading.getValue() > 25 && pb_loading.getValue() <= 50) {
                    lb_loading.setText("Iniciando Processos...");
                } else if (pb_loading.getValue() > 50 && pb_loading.getValue() <= 75) {
                    lb_loading.setText("Carregando Banco de Dados...");
                } else if (pb_loading.getValue() > 75 && pb_loading.getValue() <= 100) {
                    lb_loading.setText("Abrindo Janelas...");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro no Try-Catch do Carregamento!");
                ex.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel_loading = new javax.swing.JPanel();
        pb_loading = new javax.swing.JProgressBar();
        lb_loading = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        painel_loading.setBackground(new java.awt.Color(255, 255, 255));
        painel_loading.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pb_loading.setBackground(new java.awt.Color(153, 255, 255));
        pb_loading.setForeground(new java.awt.Color(0, 153, 255));
        pb_loading.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        pb_loading.setOpaque(true);
        pb_loading.setVerifyInputWhenFocusTarget(false);
        painel_loading.add(pb_loading, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 229, 400, 10));
        painel_loading.add(lb_loading, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 180, 14));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loading.png"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        painel_loading.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 240));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_loading, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_loading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                break;
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Loading().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lb_loading;
    private javax.swing.JPanel painel_loading;
    private javax.swing.JProgressBar pb_loading;
    // End of variables declaration//GEN-END:variables
}
