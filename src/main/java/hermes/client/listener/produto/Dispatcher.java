/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.listener.produto;

import hermes.ejb.entidades.Aplicacao;
import hermes.ejb.entidades.Descricao;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Mews
 * 
 */
public class Dispatcher<T> {
 
    private static Dispatcher instance;
    private Set<PanelListenerInterface> listeners;
 
    private Dispatcher() {
        this.listeners = new HashSet<PanelListenerInterface>();
    }
 
    public static Dispatcher getInstance() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }
 
    public void addListener(PanelListenerInterface listener) {
        this.listeners.add(listener);
    }
 
    public void removeListener(PanelListenerInterface listener) {
        this.listeners.remove(listener);
    }
 
    public void dispatchObject(T object) {
        Iterator<PanelListenerInterface> iterator = listeners.iterator();
 
        while (iterator.hasNext()) {
            PanelListenerInterface listener = (PanelListenerInterface) iterator.next();
            listener.receivedObject(object);
        }
    }
}
