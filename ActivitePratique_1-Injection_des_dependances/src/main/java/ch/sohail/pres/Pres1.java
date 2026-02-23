package ch.sohail.pres;

import ch.sohail.ext.DaoImplV2;
import ch.sohail.dao.DaoImpl;
import ch.sohail.metier.MetierImpl;

public class Pres1 {
    public static void main(String[] args) {
        DaoImplV2 dao = new DaoImplV2();
        MetierImpl metier = new MetierImpl(dao);
        //metier.setDao(dao); // Injection des dependances via le setter
        System.out.println("MetierImpl calcul : " + metier.calcul());
    }
}
