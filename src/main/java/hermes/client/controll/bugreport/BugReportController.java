package hermes.client.controll.bugreport;

import hermes.client.dao.BugReportBean;
import hermes.ejb.entidades.BugReport;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iuryk
 */
public class BugReportController {

    private BugReportBean bean;

    public BugReportController() {
        bean = new BugReportBean();
    }

    public BugReport cadastraBugReport(BugReport bugReport) {
        BugReport b = new BugReport();
        try {
            b = bean.cadastraBugReport(bugReport);
        } catch (Exception ex) {
            Logger.getLogger(BugReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
