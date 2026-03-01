package ch.sohail.app.pres;

import ch.sohail.app.metier.IMetier;
import ch.sohail.framework.IFrameworkContext;
import ch.sohail.framework.XmlFrameworkContext;

public class presXML {
    public static void main(String[] args) {
        // Le développeur démarre ton framework
        IFrameworkContext context = new XmlFrameworkContext("src/main/resources/config.xml");

// il peut récupérer son métier prêt à l'emploi !
        IMetier metier = (IMetier) context.getBean("metier");

        System.out.println("RES="+metier.calcul());
    }
}
