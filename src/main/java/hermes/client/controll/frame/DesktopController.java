package hermes.client.controll.frame;

import hermes.client.view.frame.Desktop;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author iuryk
 */
public class DesktopController {

    public void startSystemHour(JLabel label) {
        final JLabel labelHour = label;
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        Date d = new Date();
                        labelHour.setText("Hora do sistema: " + sdf.format(d));
                        sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
    }

    public void showUsername(JLabel tf_user, String username) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        tf_user.setText(sdf.format(d) + " | Logado como: " + username);
    }

}
