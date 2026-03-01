package ch.sohail.framework;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class XmlFrameworkContext implements IFrameworkContext {

    private Map<String, Object> beans = new HashMap<>();

    public XmlFrameworkContext(String xmlFilePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Beans.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Beans racineXml = (Beans) unmarshaller.unmarshal(new File(xmlFilePath));

            // --- ÉTAPE 1 : CRÉATION (ET INJECTION PAR CONSTRUCTEUR) ---
            for (Bean beanXml : racineXml.getBeans()) {
                Class<?> maClasse = Class.forName(beanXml.getClassName());
                Object monObjet = null;

                Constructor<?> constructeurAutowired = null;
                for (Constructor<?> constructeur : maClasse.getDeclaredConstructors()) {
                    if (constructeur.isAnnotationPresent(Autowired.class)) {
                        constructeurAutowired = constructeur;
                        break;
                    }
                }

                if (constructeurAutowired != null) {
                    Class<?>[] types = constructeurAutowired.getParameterTypes();
                    Object[] params = new Object[types.length];
                    for (int i = 0; i < types.length; i++) {
                        params[i] = trouverDependance(types[i]);
                    }
                    monObjet = constructeurAutowired.newInstance(params);
                } else {
                    monObjet = maClasse.newInstance();
                }

                beans.put(beanXml.getId(), monObjet);
            }

            // --- ÉTAPE 2 : INJECTION (FIELD & SETTER) ---
            injecterDependancesRestantes();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void injecterDependancesRestantes() throws Exception {
        for (Object beanInstance : beans.values()) {
            Class<?> clazz = beanInstance.getClass();

            // Field Injection
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object dependance = trouverDependance(field.getType());
                    if (dependance != null) {
                        field.setAccessible(true);
                        field.set(beanInstance, dependance);
                    }
                }
            }

            // Setter Injection
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Autowired.class) && method.getParameterCount() == 1) {
                    Object dependance = trouverDependance(method.getParameterTypes()[0]);
                    if (dependance != null) {
                        method.invoke(beanInstance, dependance);
                    }
                }
            }
        }
    }

    private Object trouverDependance(Class<?> typeRecherche) {
        for (Object obj : beans.values()) {
            if (typeRecherche.isAssignableFrom(obj.getClass())) return obj;
        }
        return null;
    }

    @Override
    public Object getBean(String id) {
        return beans.get(id);
    }
}