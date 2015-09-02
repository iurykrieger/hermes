package hermes.client.controll.frame;

import hermes.client.dao.LoginBean;
import hermes.ejb.entidades.VendedorInfo;

/**
 *
 * @author iuryk
 */
public class LoadingController {

    private LoginBean bean;

    public LoadingController() {
        bean = new LoginBean();
    }

    public boolean load() {
        return firstConnection();
    }

    public boolean firstConnection() {
        try {
            VendedorInfo vi = bean.testConnection();
            if (vi != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
