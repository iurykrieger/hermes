package hermes.client.view.utils;

import com.hermes.components.utils.ImageFactory;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.plexus.util.FileUtils;

/**
 *
 * @author iuryk
 */
public class ImageUtils {

    public static void moveImageToPath(String imagePath, String futurePath) {
        try {
            new ImageFactory().moveFile(imagePath, futurePath);
            JOptionPane.showMessageDialog(null, "Foto movida com sucesso para " + futurePath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Impossível mover a foto para o diretório", "Alerta", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ImageIcon getImageIconByFile(String path, int width, int height) {
        try {
            ImageIcon i = new ImageIcon(path);
            i.setImage(i.getImage().getScaledInstance(width, height, 100));
            return i;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao acessar arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static boolean isValidExtension(File f) {
        String ext = FilenameUtils.getExtension(f.getAbsolutePath());
        if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg")) {
            return true;
        } else {
            return false;
        }
    }
}
