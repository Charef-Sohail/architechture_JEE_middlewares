package ch.sohail.app.metier;

import ch.sohail.app.dao.IDao;
import ch.sohail.framework.Autowired;

public class MetierImpl implements IMetier {
    @Autowired
    private IDao dao;

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    public MetierImpl() {
   }

    @Override
    public double calcul() {
        double t = dao.getData();
        double result = t * 0.5; // regle metier
        return result;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
