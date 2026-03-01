package ch.sohail.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 1. On dit à Java de garder cette annotation à l'exécution
// pour que notre class.forName puisse la lire.
@Retention(RetentionPolicy.RUNTIME)

// 2. On dit où le développeur a le droit de la placer :
// Sur un attribut (FIELD), un constructeur (CONSTRUCTOR), ou un setter (METHOD).
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Autowired {
}