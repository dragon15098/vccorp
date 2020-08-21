package model;

import java.util.List;

public class Info {
    private int user;
    private double per_user;
    private int user_old_time;
    private List<Data> source;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public double getPer_user() {
        return per_user;
    }

    public void setPer_user(double per_user) {
        this.per_user = per_user;
    }

    public int getUser_old_time() {
        return user_old_time;
    }

    public void setUser_old_time(int user_old_time) {
        this.user_old_time = user_old_time;
    }

    public List<Data> getSource() {
        return source;
    }

    public void setSource(List<Data> source) {
        this.source = source;
    }
}
