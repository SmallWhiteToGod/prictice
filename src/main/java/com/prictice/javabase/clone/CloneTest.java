package com.prictice.javabase.clone;

/**
 * @author 苏博
 * @className: CloneTest.java
 * @package com.prictice.javabase.clone
 * @description:
 * @date 2020/2/17 10:10
 */
public class CloneTest implements Cloneable{

    //基础类型
    private int id;
    //引用类型
    private CloneSup cloneSup;

    public CloneTest(int id, CloneSup cloneSup) {
        this.id = id;
        this.cloneSup = cloneSup;
    }

    public CloneSup getCloneSup() {
        return cloneSup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCloneSup(CloneSup cloneSup) {
        this.cloneSup = cloneSup;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //浅克隆  引用类型 对象复制是的原来引用的对象
        //深克隆  引用类型 对象是克隆的新对象
        CloneTest t =(CloneTest)super.clone();
        CloneSup sup = (CloneSup)this.cloneSup.clone();
        t.setCloneSup(sup);
        return t;
    }

    @Override
    public String toString() {
        return "CloneTest{" +
                "id=" + id +
                ", cloneSup=" + cloneSup +
                '}';
    }
}
