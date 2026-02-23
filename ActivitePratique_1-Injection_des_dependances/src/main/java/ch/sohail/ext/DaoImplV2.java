package ch.sohail.ext;

import ch.sohail.dao.IDao;

public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version capteurs....");
        double t = 37;
        return t;
    }
}
