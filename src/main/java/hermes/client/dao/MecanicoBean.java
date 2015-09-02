package hermes.client.dao;

import hermes.client.core.AbstractBean;
import hermes.ejb.controller.mecanico.MecanicoRemote;
import hermes.ejb.entidades.Mecanico;
import hermes.ejb.entidades.MecanicoInfo;
import hermes.ejb.utils.EjbConstants;
import java.util.List;

public class MecanicoBean extends AbstractBean<MecanicoRemote> {

    public MecanicoBean() throws Exception {
        lookup(EjbConstants.CONTROLA_MECANICO);
    }

    public Mecanico cadastraMecanico(Mecanico mecanico) throws Exception {
        Mecanico m;
        m = ejb.cadastraMecanico(mecanico);
        return m;
    }

    public List<Mecanico> consultaMecanicos() throws Exception {
        List<Mecanico> mecanicos;
        mecanicos = ejb.consultaMecanicos();
        return mecanicos;
    }

    public MecanicoInfo cadastraMecanicoInfo(MecanicoInfo mecanicoinfo) throws Exception {
        MecanicoInfo mi;
        mi = ejb.cadastraMecanicoInfo(mecanicoinfo);
        return mi;
    }

    public List<MecanicoInfo> consultaMecanicoInfos() throws Exception {
        List<MecanicoInfo> mecanicos;
        mecanicos = ejb.consultaMecanicoInfos();
        return mecanicos;
    }

    public List<MecanicoInfo> consultaMecanicoInfosByIdAtivo() throws Exception {
        List<MecanicoInfo> mecanicos;
        mecanicos = ejb.consultaMecanicoInfoByIdAtivo();
        return mecanicos;
    }

    public void setMecanicoInfosInactive(Mecanico m) throws Exception {
        ejb.setMecanicoInfosInactive(m);
    }

    public MecanicoInfo consultaMecanicoInfoByMecanico(Mecanico m) throws Exception {
        MecanicoInfo mi;
        mi = ejb.consultaMecanicoInfoByMecanico(m);
        return mi;
    }

}
