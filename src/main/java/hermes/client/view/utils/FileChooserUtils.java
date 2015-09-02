package hermes.client.view.utils;

import com.hermes.components.HMFrames.HMPanel;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author iuryk
 */
public class FileChooserUtils {

    public static File getFile(JFileChooser fileChooser, HMPanel panel) {
        int returnVal = fileChooser.showOpenDialog(panel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                return file;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Problema ao acessar o arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getPath(JFileChooser fileChooser, HMPanel panel) {
        int returnVal = fileChooser.showSaveDialog(panel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                System.out.println("Path: " + file.getAbsolutePath());
                return file.getAbsolutePath();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Problema ao salvar no diretório", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
        }
    }
}
