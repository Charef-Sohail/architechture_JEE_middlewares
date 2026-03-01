package ch.sohail.app.pres;

import ch.sohail.app.metier.IMetier;
import ch.sohail.framework.AnnotationFrameworkContext;
import ch.sohail.framework.IFrameworkContext;

public class presANN {
    public static void main(String[] args) {
        IFrameworkContext context = new AnnotationFrameworkContext("ch.sohail");

        IMetier metier = (IMetier) context.getBean("metier");

        System.out.println("Résultat du calcul : " + metier.calcul());
    }
}
