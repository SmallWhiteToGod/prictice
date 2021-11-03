package com.prictice.javabase.collection;

import java.util.Objects;

/**
 * @author 苏博
 * @className: User.java
 * @package com.prictice.javabase
 * @description:
 * @date 2019/8/25 16:26
 */
public class User implements Comparable<User>{

    private long id;

    private String name;

    private int age;

    private String address;

    public User(long id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
        //return (int)(Math.random()*1000); // set添加时先比较hashcode，hashcode相同再比较equals
    }

    @Override
    public int compareTo(User o) {
        //return (int)(id-o.getId()); //id排序
        return (age-o.getAge());  //age 排序
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
