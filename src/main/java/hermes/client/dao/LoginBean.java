/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.login.LoginRemote;
import hermes.ejb.utils.EjbConstants;
import hermes.ejb.entidades.VendedorInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iury
 */
public class LoginBean extends AbstractBean<LoginRemote> {

    public LoginBean() {
        try {
            lookup(EjbConstants.CONTROLA_LOGIN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public VendedorInfo valida(String usu, String senha) throws Exception {
        VendedorInfo i = null;
        i = ejb.verificaVendedor(usu, senha);
        return i;
    }

    public VendedorInfo testConnection() {
        VendedorInfo i = null;
        System.out.println("Testando conexão...");
        try {
            i = ejb.testConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Retorno: " + i);
        return i;
    }
}
