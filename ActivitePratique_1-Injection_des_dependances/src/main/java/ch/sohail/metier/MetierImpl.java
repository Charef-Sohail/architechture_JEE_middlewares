package ch.sohail.metier;

import ch.sohail.dao.IDao;

public class MetierImpl implements IMetier {
    private IDao dao; // Couplage faible

    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implement l'interface IDao
     * au moment de la creation de l'objet (l'instantiation)
     */
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

    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implement l'interface IDao
     * apres instantiation
     */
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
