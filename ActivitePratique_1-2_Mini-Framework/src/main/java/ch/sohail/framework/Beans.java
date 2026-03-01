package ch.sohail.framework;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Beans {

    List<Bean> beansList = new ArrayList<>();

    @XmlElement(name = "bean")
    public List<Bean> getbeansList() {
        return beansList;
    }

    public void setbeansList() {
        this.beansList = beansList;
    }
}
