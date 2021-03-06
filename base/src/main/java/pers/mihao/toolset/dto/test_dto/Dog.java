package pers.mihao.toolset.dto.test_dto;

import java.util.ArrayList;
import java.util.List;

public class Dog {

    public Dog() {
    }

    public Dog(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Dog> getList() {
        List<Dog> list = new ArrayList();
        Dog dog = new Dog(1, "大黄");
        Dog dog2 = new Dog(2, "大黄2");
        Dog dog3 = new Dog(3, "大黄3");
        list.add(dog);
        list.add(dog2);
        list.add(dog3);
        return list;
    }


    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
