package ch.sohail.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // S'applique uniquement sur les classes
public @interface Component {
    // Permet de donner un ID au bean, ex: @Component("dao")
    String value() default "";
}