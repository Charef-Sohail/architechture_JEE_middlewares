package ch.sohail.pres;

import ch.sohail.dao.IDao;
import ch.sohail.metier.IMetier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Pres2 {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Scanner sc = new Scanner(new File("config.txt"));

        String daoClassName = sc.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.newInstance();

        String metierClassName = sc.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);
//        IMetier metier = (IMetier) cMetier.getConstructor().newInstance();
//        Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
//        setDao.invoke(metier, dao);
        System.out.println("RES="+metier.calcul());
    }

}
