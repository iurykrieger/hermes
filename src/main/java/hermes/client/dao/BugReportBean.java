package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.bugreport.BugReportRemote;
import hermes.ejb.entidades.BugReport;
import hermes.ejb.entidades.Cliente;
import hermes.ejb.utils.EjbConstants;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iuryk
 */
public class BugReportBean extends AbstractBean<BugReportRemote> {

    public BugReportBean() {
        try {
            lookup(EjbConstants.CONTROLA_BUG_REPORT);
        } catch (Exception ex) {
            Logger.getLogger(BugReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BugReport cadastraBugReport(BugReport bugReport) {
        try {
            BugReport bg;
            bg = ejb.cadastraBugReport(bugReport);
            return bg;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
}
