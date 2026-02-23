package ch.sohail.pres;

import ch.sohail.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("ch.sohail");
        IMetier metier = applicationContext.getBean(IMetier.class);
        System.out.println("Res=" + metier.calcul());
    }
}
