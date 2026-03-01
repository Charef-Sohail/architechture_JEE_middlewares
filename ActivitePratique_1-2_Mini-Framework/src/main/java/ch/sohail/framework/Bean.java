package ch.sohail.framework;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bean")
public class Bean {
    private String id;
    private String className;

    public Bean(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public Bean() {
    }
    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute(name = "class")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}