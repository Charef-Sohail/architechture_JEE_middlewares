package ch.sohail.app.metier;

import ch.sohail.app.dao.IDao;
import ch.sohail.framework.Autowired;
import ch.sohail.framework.Component;

@Component("metier")
public class MetierImpl implements IMetier {
    //@Autowired -> pour l'injection par attribut
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

    @Autowired //pour l'injection par setter
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
