package com.pb.projectbuilder.model;

/**
 * Created by jongchan on 15. 12. 11..
 */
public class RecyclerCard {
    String title;
    String m_name;
    int b_num;


    public RecyclerCard() {
    }

    public RecyclerCard(String m_name, String title, int b_num) {
        this.m_name = m_name;
        this.title = title;
        this.b_num = b_num;
    }

    public int getB_num() {
        return b_num;
    }

    public void setB_num(int b_num) {
        this.b_num = b_num;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getm_name() {
        return m_name;
    }

    public void setm_name(String m_name) {
        this.m_name = m_name;
    }

    public   String getTitle(){
        return this.title;
    }

    public RecyclerCard( String title){

        this.title=title;
    }
}