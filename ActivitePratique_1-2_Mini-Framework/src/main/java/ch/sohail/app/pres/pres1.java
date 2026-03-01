package ch.sohail.app.pres;

import ch.sohail.app.metier.IMetier;
import ch.sohail.framework.FrameworkContext;

public class pres1 {
    public static void main(String[] args) {
        // Le développeur démarre ton framework
        FrameworkContext context = new FrameworkContext("src/main/resources/config.xml");

// Et boum, il peut récupérer son métier prêt à l'emploi !
        IMetier metier = (IMetier) context.getBean("metier");
    }
}
