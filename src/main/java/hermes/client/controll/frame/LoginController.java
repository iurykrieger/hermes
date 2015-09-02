package hermes.client.controll.frame;

import hermes.client.dao.LoginBean;
import hermes.ejb.entidades.VendedorInfo;

/**
 *
 * @author iury
 */
public class LoginController {

    private LoginBean bean;

    public LoginController() {
        bean = new LoginBean();
    }

    public boolean login(String usuario, String senha) {
        try {
            LoginBean bean = new LoginBean();
            VendedorInfo vi;
            vi = bean.valida(usuario, senha);
            if (vi != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

}
