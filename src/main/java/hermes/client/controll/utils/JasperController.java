package hermes.client.controll.utils;

import hermes.ejb.entidades.OrdemDeServico;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JasperController {

    public void gerarRelatorioOrdemServico(OrdemDeServico os, JFileChooser j) {
        List<OrdemDeServico> lista = new ArrayList<OrdemDeServico>();
        lista.add(os);
        String path = abrirFileChooser(j);
        try {
            URL is = getClass().getResource("/relatorios/OrdemDeServico.jrxml");
            JasperReport reportJasper = JasperCompileManager.compileReport(is.openStream());
            JRBeanCollectionDataSource coll = new JRBeanCollectionDataSource(lista);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasper, new HashMap(), coll);
            File pdf = new File(path + "\\OS_" + os.getIdOrdemServico() + "_" + System.currentTimeMillis() + ".pdf");
            OutputStream out = null;
            out = new FileOutputStream(pdf);
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            out.flush();
            out.close();
            JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso em : " + pdf.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível gerar o relatório!");
            e.printStackTrace();
        }
    }

    public String abrirFileChooser(JFileChooser chooser) {
        String path = null;
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path = String.valueOf(chooser.getSelectedFile());
        }
        return path;
    }
}
