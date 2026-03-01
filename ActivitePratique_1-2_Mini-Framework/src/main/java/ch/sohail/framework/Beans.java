package ch.sohail.framework;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Beans {

    List<Bean> beans = new ArrayList<>();

    @XmlElement(name = "bean")
    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }
}
