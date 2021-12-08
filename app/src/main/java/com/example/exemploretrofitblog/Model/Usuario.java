package com.example.exemploretrofitblog.Model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String name;
    private int public_repos;
    private String avatar_url;

    public Usuario(){

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }
    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
