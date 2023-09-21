package com.example.uidesign;

import android.widget.TextView;

public class User_recycle_model {

    private int userpic;
    private String username;
    private String hometown;
    private String age;
    private String divider;

    public User_recycle_model(int userpic, String username, String hometown, String age, String divider) {
        this.userpic = userpic;
        this.username = username;
        this.hometown = hometown;
        this.age = age;
        this.divider = divider;
    }

    public int getUserpic() {
        return userpic;
    }

    public void setUserpic(int userpic) {
        this.userpic = userpic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDivider() {
        return divider;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }
}
