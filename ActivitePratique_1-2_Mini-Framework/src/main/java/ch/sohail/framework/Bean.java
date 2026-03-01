package ch.sohail.framework;

public class Bean {
    private String id;
    private String className;

    public Bean(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public Bean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}