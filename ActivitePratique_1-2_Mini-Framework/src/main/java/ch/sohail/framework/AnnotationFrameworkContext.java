package ch.sohail.framework;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationFrameworkContext implements IFrameworkContext {

    private Map<String, Object> beans = new HashMap<>();

    public AnnotationFrameworkContext(String basePackage) {
        try {
            // 1. Trouver toutes les classes dans le package
            List<Class<?>> classesTrouvees = scannerPackage(basePackage);

            // 2. Filtrer celles qui ont @Component et les instancier
            for (Class<?> clazz : classesTrouvees) {
                if (clazz.isAnnotationPresent(Component.class)) {
                    Component componentAnnotation = clazz.getAnnotation(Component.class);

                    // Récupérer l'ID : la valeur dans @Component("...") ou le nom de la classe par défaut
                    String beanId = componentAnnotation.value();
                    if (beanId.isEmpty()) {
                        beanId = clazz.getSimpleName().toLowerCase();
                    }

                    Object monObjet = null;
                    Constructor<?> constructeurAutowired = null;
                    for (Constructor<?> constructeur : clazz.getDeclaredConstructors()) {
                        if (constructeur.isAnnotationPresent(Autowired.class)) {
                            constructeurAutowired = constructeur;
                            break;
                        }
                    }

                    // Constructor Injection
                    if (constructeurAutowired != null) {
                        Class<?>[] types = constructeurAutowired.getParameterTypes();
                        Object[] params = new Object[types.length];
                        for (int i = 0; i < types.length; i++) {
                            params[i] = trouverDependance(types[i]);
                        }
                        monObjet = constructeurAutowired.newInstance(params);
                    } else {
                        monObjet = clazz.newInstance(); // Constructeur par défaut
                    }

                    beans.put(beanId, monObjet);
                }
            }

            // 3. Injecter les Fields et Setters (La logique est exactement la même que pour le XML !)
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

    // --- MAGIE NOIRE : Méthode utilitaire pour trouver les fichiers .class dans un dossier ---
    private List<Class<?>> scannerPackage(String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);

        if (resource != null) {
            File directory = new File(resource.getFile());
            if (directory.exists()) {
                for (File file : directory.listFiles()) {
                    if (file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().replace(".class", "");
                        classes.add(Class.forName(className));
                    } else if (file.isDirectory()) {
                        // Scan récursif pour les sous-dossiers (ex: ch.sohail.dao)
                        classes.addAll(scannerPackage(packageName + "." + file.getName()));
                    }
                }
            }
        }
        return classes;
    }

    @Override
    public Object getBean(String id) {
        return beans.get(id);
    }
}