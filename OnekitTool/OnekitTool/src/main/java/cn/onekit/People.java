package cn.onekit;

/**
 * Created by BryantCurry on 2016/12/30.
 */

public class People {
    private String id;
    private String name;
    public People(String id,String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "["+ "id:" + id + ";" + "name:" + name +"]";
    }
}
