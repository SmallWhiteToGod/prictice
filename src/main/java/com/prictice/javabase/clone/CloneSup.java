package com.prictice.javabase.clone;

/**
 * @author 苏博
 * @className: CloneSup.java
 * @package com.prictice.javabase.clone
 * @description:
 * @date 2020/2/17 10:12
 */
public class CloneSup implements Cloneable{

    private String name;

    private int age;

    public CloneSup(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
