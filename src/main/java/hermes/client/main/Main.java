package hermes.client.main;

import hermes.client.view.frame.Desktop;
import hermes.client.view.frame.Loading;
import hermes.client.view.frame.Login;
import java.awt.Frame;
import javax.swing.JOptionPane;
import org.japura.modal.Modal;

/**
 *
 * @author iuryk
 */
public class Main {

    public static Loading loading;
    public static Login login;
    public static Desktop desktop;

    public static void main(String[] args) {

        loading = new Loading();
        loading.setVisible(true);
        if (loading.loadHermes()) {
            loading.dispose();
            desktop = new Desktop();
            login = new Login();
            desktop.setVisible(true);
            login.setVisible(true);
            Modal.addModal(desktop, login);
            desktop.setState(Frame.NORMAL);
            login.setState(Frame.NORMAL);
        } else {
            JOptionPane.showMessageDialog(null, "Problemas ao conectar com o servidor. Por favor contate o administrador do sistema ou tente novamente mais tarde.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            loading.dispose();
            System.exit(0);
        }
    }
}
