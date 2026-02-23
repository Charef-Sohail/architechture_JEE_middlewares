package ch.sohail.ext;

import ch.sohail.dao.IDao;
import org.springframework.stereotype.Component;

//@Component("d2")
public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version capteurs....");
        double t = 37;
        return t;
    }
}
