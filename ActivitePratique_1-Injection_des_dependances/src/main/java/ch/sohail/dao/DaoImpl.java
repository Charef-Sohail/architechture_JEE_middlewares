package ch.sohail.dao;

import org.springframework.stereotype.Component;

@Component("d")
public class DaoImpl implements IDao {

    @Override
    public double getData() {
        System.out.println("Version base de donnees");
        double t = 40;
        return t;
    }
}
