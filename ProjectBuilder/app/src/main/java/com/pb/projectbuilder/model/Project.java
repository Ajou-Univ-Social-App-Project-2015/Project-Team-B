package com.pb.projectbuilder.model;

/**
 * Created by jongchan on 2015. 11. 6..
 */
public class Project {

    private String name; //이름


    public Project(String s) {
        name = s;
    }

    //getter,setter 메소드
    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }




}
