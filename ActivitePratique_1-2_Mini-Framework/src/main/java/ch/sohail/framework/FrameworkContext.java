package ch.sohail.framework;

import java.util.HashMap;
import java.util.Map;

public class FrameworkContext {

    // stocker les instances avec ce dic
    private Map<String, Object> beans = new HashMap();

    // ajouter un nouveau bean cree dans le conteneur
    public void addBean(String id, Object instance) {
        beans.put(id, instance);
    }

    // recuperer un bean
    public Object getBean(String id) {
        return beans.get(id);
    }
}
