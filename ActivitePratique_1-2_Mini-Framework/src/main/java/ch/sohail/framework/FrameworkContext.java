package ch.sohail.framework;

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

            //  (Bientôt, on ajoutera le code pour L'INJECTION ici !)

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
