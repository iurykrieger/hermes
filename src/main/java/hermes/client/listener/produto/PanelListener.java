package hermes.client.listener.produto;

import com.hermes.components.HMFrames.HMPanel;

/**
 *
 * @author iuryk
 */
public class PanelListener implements PanelListenerInterface{
    
    private HMPanel panel;
    
    public PanelListener(HMPanel panel){
        this.panel = panel;
    }

    public void receivedObject(Object object) {
        panel.getReceivedObject(object);
    }
    
}
