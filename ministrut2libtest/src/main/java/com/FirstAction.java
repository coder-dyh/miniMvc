package com;

import entity.Student;


public class FirstAction {

    private  int id;
    private String name;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String execute(){
        System.out.println("------execute-------");
        System.out.println("------id :" + id + "----name: " + name);

        return "success";
    }
}
