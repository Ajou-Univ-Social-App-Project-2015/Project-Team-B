package com.pb.projectbuilder.model;

/**
 * Created by jongchan on 2015. 11. 6..
 */
public class Project {

    private String name; //이름
    private String date; //생성날짜
    // private  int imgId; //이미지 리소스 아이디


    //constructor 생성자 함수로 전달받은 project정보를 멤버변수에 저장
    public Project(String name, String date ) {
        this.name = name;
        this.date = date;
        //   this.imgId = imgId;
    }

    //getter,setter 메소드
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
