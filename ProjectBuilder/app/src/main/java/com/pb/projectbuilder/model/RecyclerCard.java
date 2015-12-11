package com.pb.projectbuilder.model;

/**
 * Created by jongchan on 15. 12. 11..
 */
public class RecyclerCard {
    String title;
    String email;


    public RecyclerCard() {
    }

    public RecyclerCard(String email, String title) {
        this.email = email;
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public   String getTitle(){
        return this.title;
    }

    public RecyclerCard( String title){

        this.title=title;
    }
}