package com.example.uidesign;

public class Recycler_item_model {

    private String pic;
    private String username;
    private String age;

    public Recycler_item_model(String pic, String username, String age) {
        this.pic = pic;
        this.username = username;
        this.age = age;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
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
}
