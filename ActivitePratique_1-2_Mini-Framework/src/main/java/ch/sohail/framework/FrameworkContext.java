package ch.sohail.framework;

import java.lang.reflect.Field;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FrameworkContext {

    // stocker les instances avec ce dic
    private Map<String, Object> beans = new HashMap();

    public FrameworkContext(String xmlFilePath) {
        try {
            // 1. Initialiser JAXB pour lire la classe racine 'Beans'
            JAXBContext jaxbContext = JAXBContext.newInstance(Beans.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // 2. Lire le fichier XML
            Beans racineXml = (Beans) unmarshaller.unmarshal(new File(xmlFilePath));

            // 3. Boucler sur chaque <bean> trouvé dans le XML
            for (Bean beanXml : racineXml.getBeans()) {

                Class<?> maClasse = Class.forName(beanXml.getClassName());
                Object monObjet = maClasse.newInstance();

                // 4. Stocker l'objet dans notre Map avec son ID
                beans.put(beanXml.getId(), monObjet);
            }

            // On repasse sur tous les objets qu'on a créés
            for (Object beanInstance : beans.values()) {

                // On récupère la classe de l'objet actuel (ex: MetierImpl)
                Class<?> clazz = beanInstance.getClass();

                // On récupère TOUS ses attributs (ex: private IDao dao;)
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    // Est-ce que cet attribut possède notre annotation @Autowired ?
                    if (field.isAnnotationPresent(Autowired.class)) {

                        // 1. On cherche de quel type est cet attribut (ex: IDao)
                        Class<?> typeDuField = field.getType();

                        // 2. On cherche dans notre Map un objet qui correspond à ce type
                        Object dependanceAInjecter = null;
                        for (Object objDansMap : beans.values()) {
                            // Si l'objet dans la map est compatible avec le type du field
                            if (typeDuField.isAssignableFrom(objDansMap.getClass())) {
                                dependanceAInjecter = objDansMap;
                                break;
                            }
                        }

                        // 3. On injecte l'objet trouvé dans l'attribut !
                        if (dependanceAInjecter != null) {
                            try {
                                // C'est ici qu'on fait l'injection : field.set(objetCible, valeurAInjecter)
                                field.setAccessible(true);
                                field.set(beanInstance, dependanceAInjecter);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ajouter un nouveau bean cree dans le conteneur
    public void addBean(String id, Object instance) {
        beans.put(id, instance);
    }

    // recuperer un bean
    public Object getBean(String id) {
        return beans.get(id);
    }
}
