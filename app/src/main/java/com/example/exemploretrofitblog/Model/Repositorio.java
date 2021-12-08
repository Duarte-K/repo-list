package com.example.exemploretrofitblog.Model;

public class Repositorio {
    private  String name;
    private  String html_url;

    public Repositorio(String name, String url){
        this.name = name;
        this.html_url = url;
    }

    @Override
    public String toString(){
        return "Nome: " +this.name + "url: " +this.html_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
