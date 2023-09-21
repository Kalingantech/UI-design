package com.example.uidesign.Login;

import org.jetbrains.annotations.NotNull;

public class Profile_Template {

    private String username;
    private String age;
    private String phoneno;
    private String pic;

    public Profile_Template() {
    }

    public Profile_Template(String username, String age, String phoneno, String pic) {
        this.username = username;
        this.age = age;
        this.phoneno = phoneno;
        this.pic = pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
