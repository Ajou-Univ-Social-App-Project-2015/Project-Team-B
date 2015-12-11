package com.pb.projectbuilder.model;

/**
 * Created by sanghee on 2015-11-25.
 */
public class Comment {
    private String comment; //이름
    private String date; //생성날짜

    public Comment(String comment, String date ) {
        this.comment = comment;
        this.date = date;
        //   this.imgId = imgId;
    }

    //getter,setter 메소드
    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }


}