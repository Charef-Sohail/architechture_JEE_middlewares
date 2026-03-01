package ch.sohail.app.dao;

public class DaoImpl implements IDao {

    @Override
    public double getData() {
        System.out.println("Version base de donnees");
        double t = 40;
        return t;
    }
}
